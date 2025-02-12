package com.iecas.communityauth;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.iecas.communityauth.dao.AuthUserDao;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.feign.AuthServiceFeign;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityAuthApplicationTests {

    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwNmY5NWU5MS05MWVmLTQzZmEtOWYzNS1iZmZiNGI1MTI0ZWYiLCJpc3MiOiJzeXN0ZW0iLCJkYXRhIjp7ImlkIjoxODg4ODI5NzYyODc3NTM4MzA1LCJhY2NvdW50Tm9uRXhwaXJlZCI6MSwiYWNjb3VudE5vbkxvY2tlZCI6MSwiY3JlZGVudGlhbHNOb25FeHBpcmVkIjoxLCJkZWxldGVkIjowLCJlbmFibGVkIjoxLCJwYXNzd29yZCI6bnVsbCwicm9sZUlkIjozLCJlbWFpbCI6Ijk0NTg1NTQ1NkBxcS5jb20iLCJwaG9uZSI6bnVsbCwibGFzdExvZ2luSXAiOm51bGwsImxhc3RMb2dpblRpbWUiOiIyMDI1LTAyLTEwIDEzOjU4OjAyIn0sInN1YiI6Ijk0NTg1NTQ1NkBxcS5jb20iLCJpYXQiOjE3MzkzMzU1MDMsImV4cCI6MTczOTk0MDMwM30.G2nylb9WTQN7heehAYMkLJ_UPCOCOkthHyt-zK1QwcE";

    @Autowired
    AuthUserDao authUserDao;

    @Autowired
    AuthServiceFeign authServiceFeign;

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


    /**
     * 解析token测试
     */
    @Test
    void parseTokenTest(){
        CommonResult result = authServiceFeign.parseToken(TOKEN);
        Object resultData = result.getData();
        JSONObject parseJSONObject = JSON.parseObject(JSON.toJSONString(resultData));
        String sub = parseJSONObject.getString("sub");
        AuthUser authUser = parseJSONObject.getObject("parsedData", AuthUser.class);
        System.out.println("sub = " + sub);
        System.out.println("authUser = " + authUser);
    }

}
