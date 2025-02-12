package com.iecas.communityauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityauth.dao.AuthUserDao;
import com.iecas.communityauth.dto.LoginDTO;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communityauth.dto.ResetDTO;
import com.iecas.communityauth.entity.LoginUserInfo;
import com.iecas.communityauth.service.AuthRolePermissionService;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communityauth.utils.JwtUtils;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.event.UserRegisterEvent;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import com.iecas.communitycommon.model.auth.vo.TokenVO;
import com.iecas.communitycommon.utils.DateTimeUtils;
import com.iecas.communitycommon.utils.MailUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * (AuthUser)表服务实现类
 *
 * @author guox
 * @since 2025-02-05 20:32:13
 */
@Service("authUserService")
public class AuthUserServiceImpl extends ServiceImpl<AuthUserDao, AuthUser> implements AuthUserService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    @Transactional
    public void register(RegisterDTO registerDTO, HttpServletRequest request) {
        if (registerDTO.getUsername() == null) {
            throw new RuntimeException("username参数不能为null");
        }

        // 获取验证码并判断验证码是否正确
        String authCode = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_CODE_REGISTER.getPath(registerDTO.getUsername()));
        if (!registerDTO.getAuthCode().equalsIgnoreCase(authCode)) {
            throw new CommonException("验证码错误");
        }
        else {
            // 判断当前账户是否已经被注册
            if (baseMapper.exists(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getEmail, registerDTO.getUsername()))){
                throw new CommonException("当前用户已经被注册");
            }

            // 保存用户信息
            AuthUser authUser = new AuthUser();
            if(MailUtils.checkEmailIsCorrect(registerDTO.getUsername())){
                authUser.setEmail(registerDTO.getUsername());
            }
            else if (registerDTO.getUsername().matches("\\d{11}")){
                authUser.setPhone(registerDTO.getUsername());
            }
            else {
                throw new RuntimeException("username参数并非手机号或邮箱");
            }
            authUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            // 获取用户ip
            authUser.setLastLoginIp(request.getRemoteAddr());
            authUser.setLastLoginTime(DateTimeUtils.getFormatDateTime(System.currentTimeMillis()));
            baseMapper.insert(authUser);

            // 发送RabbitMQ用户注册消息 -> 用户微服务: 同步注册信息
            UserRegisterEvent userRegisterEvent = UserRegisterEvent.builder().authUserId(authUser.getId())
                    .email(registerDTO.getUsername()).build();
            rabbitTemplate.convertAndSend("registerDirectExchange", "user.register", userRegisterEvent);

            // 删除redis中的验证码
            stringRedisTemplate.delete(RedisPrefix.AUTH_CODE_REGISTER.getPath(registerDTO.getUsername()));
        }
    }


    @Override
    public LoginUserInfo queryByUserEmail(String email) {

        // 查询用户信息与权限列表
        AuthUser authUser = baseMapper.selectOne(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getEmail, email));
        List<String> permissionList = baseMapper.queryUserPermissionsById(authUser.getId());

        // 拷贝用户权限信息与权限列表到LoginUserInfo对象中
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtils.copyProperties(authUser, loginUserInfo);
        loginUserInfo.setAuthorities(permissionList);

        return loginUserInfo;
    }


    @Override
    public LoginUserInfo queryByUserPhone(String phone) {
        LoginUserInfo loginUserInfo = new LoginUserInfo();

        // 查询用户信息与权限列表
        AuthUser authUser = baseMapper.selectOne(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getPhone, phone));
        List<String> permissionList = baseMapper.queryUserPermissionsById(authUser.getId());

        // 拷贝用户权限信息与权限列表到LoginUserInfo对象中
        BeanUtils.copyProperties(authUser, loginUserInfo);
        loginUserInfo.setAuthorities(permissionList);

        return loginUserInfo;
    }


    @Override
    public List<String> queryPermissionById(Long id) {
        return baseMapper.queryUserPermissionsById(id);
    }


    @Override
    public void reset(ResetDTO resetDTO) {
        // 检测当前用户是否注册
        AuthUser currentUser = baseMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getEmail, resetDTO.getEmail()));
        if (currentUser == null){
            throw new CommonException("当前用户未注册");
        }

        // 比对redis中的验证码
        String authCode = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_CODE_RESET.getPath(resetDTO.getEmail()));
        if (!resetDTO.getAuthCode().equalsIgnoreCase(authCode)) {
            throw new CommonException("验证码错误");
        }

        // 重新设置密码并更新密码
        currentUser.setPassword(passwordEncoder.encode(resetDTO.getPassword()));
        baseMapper.updateById(currentUser);
        // 删除验证码
        stringRedisTemplate.delete(RedisPrefix.AUTH_CODE_RESET.getPath(resetDTO.getEmail()));
        // 删除redis中的token信息
        stringRedisTemplate.delete(RedisPrefix.AUTH_LOGIN_TOKEN.getPath(resetDTO.getEmail()));
    }


    @Override
    public String login(LoginDTO loginDTO) {
        // 检测当前用户是否注册
        AuthUser currentUser = baseMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getEmail, loginDTO.getEmail()));
        if (currentUser != null){

            // 密码登录
            if (loginDTO.getPassword() != null && StringUtils.hasLength(loginDTO.getPassword())){
                if (passwordEncoder.matches(loginDTO.getPassword(), currentUser.getPassword())){
                    currentUser.setPassword(null);
                    String token = JwtUtils.createToken(currentUser.getEmail(), currentUser);
                    // 保存用户token至redis中
                    stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_LOGIN_TOKEN.getPath(loginDTO.getEmail()),
                            token, 7, TimeUnit.DAYS);
                    return token;
                }
            }
            // 验证码登录
            else if(loginDTO.getAuthCode() != null && StringUtils.hasLength(loginDTO.getAuthCode())){
                // 从redis中获取登录验证码
                String code = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_LOGIN_TOKEN.getPath(loginDTO.getEmail()));
                if (code != null && code.equals(loginDTO.getAuthCode())){
                    currentUser.setPassword(null);
                    // 删除验证码
                    stringRedisTemplate.delete(RedisPrefix.AUTH_LOGIN_TOKEN.getPath(loginDTO.getEmail()));
                    String token = JwtUtils.createToken(currentUser.getEmail(), currentUser);
                    // 保存用户token至redis中
                    stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_LOGIN_TOKEN.getPath(loginDTO.getEmail()),
                            token, 7, TimeUnit.DAYS);
                    return token;
                }
            }
        }
        throw new CommonException("当前用户未注册或密码错误");
    }


    @Override
    public TokenVO parseToken(String token) {
        TokenVO tokenVO = new TokenVO();
        try {
            Claims claims = JwtUtils.parseToken(token);
            tokenVO.setMessage("验证成功");
            tokenVO.setStatus(true);
            tokenVO.setParsedData(claims);
        } catch (Exception e) {
            tokenVO.setMessage(e.getMessage());
            return tokenVO;
        }
        return tokenVO;
    }
}

