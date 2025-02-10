/**
 * @Time: 2025/2/9 19:30
 * @Author: guoxun
 * @File: MessageListener
 * @Description:
 */

package com.iecas.communityuserservice.service.impl;


import com.iecas.communitycommon.event.UserRegisterEvent;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communityuserservice.service.UserInfoService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Slf4j
@Service("messageListener")
public class MessageListener {

    @Autowired
    UserInfoService userInfoService;


    /**
     * 处理用户注册消息队列中的信息。
     * 该方法用于接收并处理来自用户注册消息队列（`registerQueue`）的消息。消息中的用户注册事件信息（`UserRegisterEvent`）会被处理并保存用户信息。
     * 在成功处理消息后，会显式地向 RabbitMQ 发送手动确认（`basicAck`）。如果处理过程中发生异常，则会拒绝该消息并重新入队（`basicNack`）。
     * @param userRegisterEvent 由 authService 传递的用户注册事件信息，包含用户注册的相关数据，如用户 ID 和邮箱。
     * @param channel RabbitMQ 消息通道，允许进行消息确认或拒绝。
     * @param message RabbitMQ 消息对象，包含消息的相关元数据，如 `deliveryTag`，用于确认消息。
     * @throws IOException 如果在处理消息时发生 IO 异常，则会抛出该异常。
     */
    @RabbitListener(queues = "registerQueue", containerFactory = "rabbitListenerManualAckContainerFactory")
    public void handleRegisterQueueMessage(UserRegisterEvent userRegisterEvent, Channel channel, Message message) throws IOException {
        try{
            UserInfo userInfo = UserInfo.builder().authUserId(userRegisterEvent.getAuthUserId())
                    .email(userRegisterEvent.getEmail()).build();
            userInfoService.save(userInfo);

            // 手动确认消息
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag, false);
            log.debug("注册信息已成功写入用户信息表");
        } catch (Exception e) {
            // 消息处理失败, 拒绝消息并重新入队, 后续继续尝试
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicNack(deliveryTag, false, true);
            log.error("user-service: handleRegisterQueueMessage -> 用户注册消息队列处理消息异常", e);
        }
    }
}
