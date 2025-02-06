package com.iecas.communityauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CommunityAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityAuthApplication.class, args);
    }

}
