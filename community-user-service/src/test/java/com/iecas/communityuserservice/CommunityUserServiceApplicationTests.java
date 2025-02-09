package com.iecas.communityuserservice;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityUserServiceApplicationTests {

    @Autowired
    RabbitAdmin rabbitAdmin;


    /**
     * 清空registerQueue队列
     */
    @Test
    void clearRegisterQueue(){
        rabbitAdmin.purgeQueue("registerQueue");
    }

    @Test
    void contextLoads() {
    }

}
