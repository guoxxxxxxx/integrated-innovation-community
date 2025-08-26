package com.iecas.communityuserservice.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.exception.AuthException;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.feign.AuthServiceFeign;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.utils.CommonResultUtils;
import com.iecas.communitycommon.utils.RandomExpiredTime;
import com.iecas.communityuserservice.dao.UserInfoDao;
import com.iecas.communityuserservice.pojo.UserInfoDTO;
import com.iecas.communityuserservice.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * (UserInfo)表服务实现类
 *
 * @author guox
 * @since 2025-02-05 20:40:53
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    AuthServiceFeign authServiceFeign;


    @Override
    public UserInfo queryUserInfoByToken(String token) {
        // 从缓存中查询
        String userInfoJson = stringRedisTemplate.opsForValue().get(RedisPrefix.USER_TOKEN_MAPPING_USERINFO.getPath(token));
        if (StringUtils.hasLength(userInfoJson)){
            // 命中时更新缓存过期时间
            stringRedisTemplate.expire(RedisPrefix.USER_TOKEN_MAPPING_USERINFO.getPath(token),
                    RandomExpiredTime.getRandomExpiredTime(), TimeUnit.MINUTES);
            return JSON.parseObject(userInfoJson, UserInfo.class);
        }
        // 未命中, 查询数据库
        CommonResult commonResult = authServiceFeign.parseToken(token);
        JSONObject parseCommonResult = CommonResultUtils.parseCommonResult(commonResult);

        if(!parseCommonResult.getBoolean("status")){
            throw new CommonException("token 不合法/失效");
        }

        JSONObject parsedData = JSON.parseObject(JSON.toJSONString(parseCommonResult.get("parsedData")));
        String currentUserEmail = parsedData.getString("sub");
        UserInfo userInfo = baseMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getEmail, currentUserEmail));
        // 添加查询结果至缓存
        stringRedisTemplate.opsForValue().set(RedisPrefix.USER_TOKEN_MAPPING_USERINFO.getPath(token), JSON.toJSONString(userInfo),
                RandomExpiredTime.getRandomExpiredTime(), TimeUnit.MINUTES);
        return userInfo;
    }


    @Override
    public UserInfo queryUserInfoByEmail(String email) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getEmail, email));
    }


    @Override
    public List<UserInfo> queryUserInfoByIds(List<Long> ids) {
        List<UserInfo> userInfoList = baseMapper.selectBatchIds(ids);
        return userInfoList;
    }


    @Override
    public Map<Long, UserInfo> queryUserInfoByIds2Map(List<Long> ids) {
        if (ids == null || ids.size() == 0){
            return new HashMap<>();
        }
        Map<Long, UserInfo> userInfoMap = baseMapper.selectUserInfoByIds2Map(ids);
        return userInfoMap;
    }


    @Override
    public UserInfo updateUserInfoById(UserInfoDTO dto) {
        // 鉴权
        UserInfo currentUserInfo = UserThreadLocal.getUserInfo();
        if (!Objects.equals(dto.getId(), currentUserInfo.getId())){
            throw new AuthException("无权限！");
        }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(dto, userInfo);

        baseMapper.updateById(userInfo);
        userInfo = baseMapper.selectById(dto.getId());
        return userInfo;
    }
}

