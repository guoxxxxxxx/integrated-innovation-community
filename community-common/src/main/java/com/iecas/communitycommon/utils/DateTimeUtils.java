/**
 * @Time: 2025/2/10 13:55
 * @Author: guoxun
 * @File: DateTimeUtils
 * @Description:
 */

package com.iecas.communitycommon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    /**
     * 获取当前的时间格式化为 yyyy-MM-dd HH:mm:ss格式
     * @return {@link String}
     */
    public static String getCurrentDatetime(){
        return dateFormat.format(new Date());
    }


    /**
     * 将给定的时间戳转换为格式化的日期时间字符串。
     * @param timestamp 时间戳，表示自1970年1月1日以来的毫秒数
     * @return 返回格式化后的日期时间字符串，格式由 `dateFormat` 定义
     */
    public static String getFormatDateTime(Long timestamp){
        return dateFormat.format(timestamp);
    }
}
