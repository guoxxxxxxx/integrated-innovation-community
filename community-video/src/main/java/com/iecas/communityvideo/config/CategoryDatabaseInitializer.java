package com.iecas.communityvideo.config;

import com.iecas.communitycommon.model.video.entity.VideoCategoryInfo;
import com.iecas.communityvideo.service.VideoCategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time: 2025/8/25 20:35
 * @Author: guox
 * @File: CategoryDatabaseInitializer
 * @Description: 视频类别自动初始化脚本
 */
@Component
@Slf4j
public class CategoryDatabaseInitializer implements CommandLineRunner {


    @Resource
    VideoCategoryService videoCategoryService;

    @Override
    public void run(String... args) throws Exception {
        log.info("正在初始化视频数据类别表");

        if (videoCategoryService.count() == 0){
            log.info("检测到视频类别表未进行初始化，正在进行初始化...");
            String[] categoryArrays = {"音乐", "游戏", "动画", "科技", "生活", "美食", "体育", "其他"};
            List<VideoCategoryInfo> batchSaves = new ArrayList<>();
            for(String e : categoryArrays){
                VideoCategoryInfo t = new VideoCategoryInfo();
                t.setCategory(e);
                batchSaves.add(t);
            }
            videoCategoryService.saveBatch(batchSaves);
            log.info("视频类别数据库初始化完成！");
        }
        else {
            log.info("检测到类别数据库中已存在数据，跳过初始化过程...");
        }
    }
}
