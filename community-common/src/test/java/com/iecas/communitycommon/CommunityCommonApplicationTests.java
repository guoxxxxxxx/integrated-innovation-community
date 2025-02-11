package com.iecas.communitycommon;

import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.feign.AuthServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class CommunityCommonApplicationTests {


    @Autowired
    AuthServiceFeign authServiceFeign;


    @Test
    void testFeign() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1NzgwMjkyMy1iYWNhLTQzNmUtOTY5YS0wYzIzMTE0OTFiNzIiLCJpc3MiOiJzeXN0ZW0iLCJzdWIiOiI5NDU4NTU0NTZAcXEuY29tIiwiaWF0IjoxNzM5MjczNzA1LCJleHAiOjE3Mzk4Nzg1MDV9.wKtYlIg1E5FPUF-FbC94UGArL9cLDSlwxnvvSCXtDW8";
        CommonResult commonResult = authServiceFeign.parseToken(token);
        log.info(commonResult.toString());
    }

}
