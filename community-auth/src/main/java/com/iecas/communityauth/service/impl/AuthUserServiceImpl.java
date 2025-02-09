package com.iecas.communityauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityauth.dao.AuthUserDao;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communityauth.entity.LoginUserInfo;
import com.iecas.communityauth.service.AuthRolePermissionService;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.event.UserRegisterEvent;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import com.iecas.communitycommon.utils.MailUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void register(RegisterDTO registerDTO) {
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

            // 注册逻辑
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
}

