/**
 * @Time: 2025/2/12 14:45
 * @Author: guoxun
 * @File: RandomExpiredTime
 * @Description: 获取随机的过期时间
 */

package com.iecas.communitycommon.utils;

public class RandomExpiredTime {

    // 以分钟为单位
    private static final int baseTime = 5 * 24 * 60;

    private static final int baseScope = 100;


    /**
     * 生成一个随机的过期时间
     * @return {@link Integer} 单位分钟
     */
    public static int getRandomExpiredTime(){
        return baseTime + (int)(Math.random() * baseScope);
    }


    /**
     * 生成一个随机的过期时间
     * @param baseTime 基线时间
     * @return {@link Integer} 单位分钟
     */
    public static int getRandomExpiredTime(int baseTime){
        return baseTime + (int)(Math.random() * baseScope);
    }


    /**
     * 生成一个随机的过期时间
     * @param baseTime 基线时间
     * @param baseScope 随机范围
     * @return {@link Integer} 单位分钟
     */
    public static int getRandomExpiredTime(int baseTime, int baseScope){
        return baseTime + (int)(Math.random() * baseScope);
    }
}
