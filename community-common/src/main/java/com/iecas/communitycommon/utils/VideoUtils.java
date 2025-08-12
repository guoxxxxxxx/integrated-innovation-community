package com.iecas.communitycommon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    /**
     * 获取视频的宽高分辨率
     * @param ffmpegPath ffmpeg可执行路径
     * @param inputPath 视频文件路径
     * @return int[]{width, height}
     */
    public static int[] getVideoResolution(String ffmpegPath, String inputPath) throws IOException, InterruptedException {
        ProcessBuilder probe = new ProcessBuilder(
                ffmpegPath, "-i", inputPath
        );
        probe.redirectErrorStream(true); // 将错误输出合并到标准输出
        Process p = probe.start();

        String output = new String(p.getInputStream().readAllBytes());
        p.waitFor();

        // 匹配分辨率模式，如 1920x1080
        Pattern pattern = Pattern.compile("(\\d{2,5})x(\\d{2,5})");
        Matcher matcher = pattern.matcher(output);

        int width = 0, height = 0;
        while (matcher.find()) {
            width = Integer.parseInt(matcher.group(1));
            height = Integer.parseInt(matcher.group(2));
            // 防止匹配到像素比等无关数字，只取合理范围
            if (width >= 100 && height >= 100) {
                break;
            }
        }

        return new int[]{width, height};
    }
}
