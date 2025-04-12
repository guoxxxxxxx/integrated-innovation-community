package com.iecas.communityvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.iecas.communitycommon.feign")
public class CommunityVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityVideoApplication.class, args);
    }

}
