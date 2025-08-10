package com.iecas.communityfile.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Time: 2025/8/7 22:20
 * @Author: guox
 * @File: GlobalThreadPools
 * @Description: file服务全局线程池
 */
public class GlobalThreadPools {

    public static ExecutorService pool = Executors.newFixedThreadPool(5);
}
