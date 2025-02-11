package com.iecas.communityuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.iecas.communitycommon.feign")
@EnableDiscoveryClient
public class CommunityUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityUserServiceApplication.class, args);
    }

}
