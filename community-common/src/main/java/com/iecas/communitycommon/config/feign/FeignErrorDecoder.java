/**
 * @Time: 2025/2/21 11:10
 * @Author: guoxun
 * @File: FeignErrorDecoder
 * @Description:
 */

package com.iecas.communitycommon.config.feign;


import com.alibaba.fastjson2.JSON;
import com.iecas.communitycommon.entity.FeignResponseEntity;
import com.iecas.communitycommon.exception.AuthException;
import com.iecas.communitycommon.exception.CommonException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;


@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.body().asInputStream()));
            StringWriter writer = new StringWriter();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                writer.write(line);
                writer.write("\n");
            }
            FeignResponseEntity feignResponseEntity = JSON.parseObject(writer.toString(), FeignResponseEntity.class);
            if (feignResponseEntity.getPath().split("/")[1].equals("auth")){
                throw new AuthException(feignResponseEntity.getMessage());
            }
            else {
                throw new RuntimeException(feignResponseEntity.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
