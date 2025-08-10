package com.iecas.communitycommon.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Time: 2025/8/9 22:33
 * @Author: guox
 * @File: VideoUtils
 * @Description: 视频处理工具类
 */
public class VideoUtils {

    /**
     * 获取视频时长（秒）
     * @param videoPath 视频文件路径
     * @return 视频时长（秒），获取失败返回 -1
     */
    public static double getVideoDurationSeconds(String videoPath) {
        try {
            String[] cmd = {
                    "ffprobe",
                    "-v", "error",
                    "-show_entries", "format=duration",
                    "-of", "default=noprint_wrappers=1:nokey=1",
                    videoPath
            };

            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null) {
                    return Double.parseDouble(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 失败返回 -1
    }

    /**
     * 获取视频时长（格式化为 HH:mm:ss）
     * @param videoPath 视频文件路径
     * @return 格式化时间字符串，获取失败返回 null
     */
    public static String getVideoDurationFormatted(String videoPath) {
        double seconds = getVideoDurationSeconds(videoPath);
        if (seconds < 0) {
            return null;
        }
        return formatTime(seconds);
    }


    /**
     * 将秒转化为时分秒的形式
     * @param seconds 秒
     * @return 格式化后的字符
     */
    private static String formatTime(double seconds) {
        int totalSeconds = (int) Math.round(seconds);
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
