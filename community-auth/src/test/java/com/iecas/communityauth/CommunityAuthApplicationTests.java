package com.iecas.communityauth;

import com.iecas.communityauth.dao.AuthUserDao;
import com.iecas.communityauth.service.AuthUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityAuthApplicationTests {

    @Autowired
    AuthUserDao authUserDao;

    @Test
    void contextLoads() {
    }


    /**
     * 根据用户id查询用户权限信息测试
     */
    @Test
    void queryUserPermissionByIdTest(){
        List<String> result = authUserDao.queryUserPermissionsById(1L);
        System.out.println(result);
    }

}
