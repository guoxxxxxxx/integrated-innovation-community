package com.iecas.communityauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityauth.dao.AuthRoleDao;
import com.iecas.communityauth.service.AuthRoleService;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.model.auth.entity.AuthRole;
import com.iecas.communitycommon.utils.RandomExpiredTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * (AuthRole)表服务实现类
 *
 * @author guox
 * @since 2025-02-05 20:31:19
 */
@Service("authRoleService")
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleDao, AuthRole> implements AuthRoleService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public String queryRoleNameById(Long id) {
        // 尝试从redis中查询
        String roleName = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_ID_MAPPING_ROLE.getPath(String.valueOf(id)));
        if (StringUtils.hasLength(roleName)){
            // 刷新缓存过期时间
            stringRedisTemplate.expire(RedisPrefix.AUTH_ID_MAPPING_ROLE.getPath(String.valueOf(id)),
                    RandomExpiredTime.getRandomExpiredTime(), TimeUnit.MINUTES );
            return roleName;
        }
        else {
            // 未命中从数据库中查询
            AuthRole authRole = baseMapper.selectOne(new LambdaQueryWrapper<AuthRole>()
                    .eq(AuthRole::getId, id));
            if (authRole != null){
                // 添加缓存保留时间随机数, 避免缓存雪崩
                stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_ID_MAPPING_ROLE.getPath(String.valueOf(id))
                        , authRole.getName(), RandomExpiredTime.getRandomExpiredTime(), TimeUnit.MINUTES);
                if (StringUtils.hasLength(authRole.getName())){
                    return authRole.getName();
                }
            }
        }
        return null;
    }
}

