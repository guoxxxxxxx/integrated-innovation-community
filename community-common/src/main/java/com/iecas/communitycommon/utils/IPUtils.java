package com.iecas.communitycommon.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @Time: 2025/8/16 17:37
 * @Author: guox
 * @File: IPUtils
 * @Description: 一些常用的方法
 */
public class IPUtils {


    /**
     * 获取用户请求的ip地址
     * @param request 请求servlet
     * @return 用户的ip地址
     */
    public static String getIp(HttpServletRequest request){
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ip = request.getRemoteAddr();
        }

        // 多级代理情况，第一个 IP 才是真实 IP
        if (ip != null && ip.length() > 15 && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}
