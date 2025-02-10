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


    /**
     * 获取当前的时间格式化为 yyyy-MM-dd HH:mm:ss格式
     * @return {@link String}
     */
    public static String getCurrentDatetime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(new Date());
    }
}
