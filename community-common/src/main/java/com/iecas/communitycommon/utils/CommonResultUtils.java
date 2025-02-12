/**
 * @Time: 2025/2/11 21:24
 * @Author: guoxun
 * @File: CommonResultUtils
 * @Description:
 */

package com.iecas.communitycommon.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.iecas.communitycommon.common.CommonResult;

public class CommonResultUtils {


    /**
     * 将CommonResult中的data解析成JSON字符串
     * @param commonResult 需要解析的响应体
     * @return JSONObject
     */
    public static JSONObject parseCommonResult(CommonResult commonResult) {
        return JSON.parseObject(commonResult.getJsonData());
    }


    /**
     * 解析 CommonResult 对象中的 data 数据并转换为指定类型的对象。
     * @param commonResult 包含 data 数据的 CommonResult 对象
     * @param clazz 目标类型的 Class 对象，用于将 JSON 数据转换为指定类型
     * @param <T> 目标类型的泛型类型参数
     * @return 返回解析后的指定类型的对象
     */
    public static <T> T parseCommonResult(CommonResult commonResult, Class<T> clazz) {
        return JSON.parseObject(commonResult.getJsonData(), clazz);
    }
}
