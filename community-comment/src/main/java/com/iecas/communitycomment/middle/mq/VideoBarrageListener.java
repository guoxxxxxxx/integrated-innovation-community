package com.iecas.communitycomment.middle.mq;

import com.alibaba.fastjson2.JSON;
import com.iecas.communitycomment.config.RabbitMQVideoBarrageConfig;
import com.iecas.communitycomment.dao.VideoBarrageMessageRepository;
import com.iecas.communitycomment.table.MGVideoBarrageMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Time: 2025/8/29 21:33
 * @Author: guox
 * @File: VideoBarrageListener
 * @Description: 
 */
@Slf4j
@Service("video-barrage-listener")
public class VideoBarrageListener {

    @Resource
    VideoBarrageMessageRepository videoBarrageMessageRepository;


    /**
     * 内存缓冲队列，用于批量保存消息
     */
    private final List<MGVideoBarrageMessage> buffer = Collections.synchronizedList(new ArrayList<>());

    /**
     * 批量入库阈值
     */
    private static final int SAVE_BATCH_SIZE = 50;


    /**
     * 定时任务，每隔2000毫秒判断一次，是否需要刷新缓冲区
     */
    @Scheduled(fixedRate = 2000)
    public void flushBuffer(){
        List <MGVideoBarrageMessage> toSave = null;
        synchronized (buffer) {
            if (!buffer.isEmpty()) {
                toSave = new ArrayList<>(buffer);
                buffer.clear();
            }
        }

        if (toSave != null){
            videoBarrageMessageRepository.saveAll(toSave);
            log.info("批量保存弹幕共: {} 条", toSave.size());
        }
    }


    @RabbitListener(
        queues = RabbitMQVideoBarrageConfig.BARRAGE_QUEUE,
        containerFactory = "rabbitListenerContainerFactory"
    )
    public void handleVideoBarrageMessage(String message){
        try {
            MGVideoBarrageMessage barrageMessage = JSON.parseObject(message, MGVideoBarrageMessage.class);
            buffer.add(barrageMessage);

            // 判断是否达到阈值
            if (buffer.size() >= SAVE_BATCH_SIZE){
                flushBuffer();
            }
        } catch (Exception e) {
            log.error("消息处理异常");
        }
    }
}
