/**
 * @Time: 2025/2/17 16:54
 * @Author: guoxun
 * @File: FFmpegServiceImpl
 * @Description:
 */

package com.iecas.communityfile.service.impl;


import com.iecas.communityfile.service.FFmpegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service("ffmpeg-Service")
@Slf4j
public class FFmpegServiceImpl implements FFmpegService {

    private static final String FFMPEG_PATH = "ffmpeg";


    @Override
    public boolean convertVideo(String inputPath, String outputPath) {
        ProcessBuilder processBuilder = new ProcessBuilder(
                FFMPEG_PATH, "-i", inputPath,  // 输入文件路径
                "-c:v", "libx264",            // 使用 libx264 编码器进行视频编码
                "-preset", "fast",            // 使用 'fast' 编码预设，平衡编码速度和质量
                "-crf", "22",                 // 设置 CRF (Constant Rate Factor)，控制视频质量。数值越低质量越高
                "-c:a", "aac",                // 使用 AAC 编码器进行音频编码
                "-b:a", "128k",               // 设置音频码率为 128kbps
                outputPath                    // 输出文件路径
        );
        return executeCommand(processBuilder);
    }


    @Override
    public boolean generateHLS(String inputPath, String outputPath) {
        // 设置 FFmpeg 命令，生成 HLS 流和切片
        ProcessBuilder builder = new ProcessBuilder(
                FFMPEG_PATH, "-i", inputPath,              // 输入文件路径
                "-c:v", "libx264", "-b:v", "2500k",       // 使用 libx264 编码器，设置视频码率为 2500k
                "-c:a", "aac", "-b:a", "128k",            // 使用 AAC 编码器，设置音频码率为 128k
                "-f", "hls",                              // 设置输出格式为 HLS
                "-hls_time", "5",                         // 设置每个视频切片的时长为 5 秒
                "-hls_list_size", "0",                    // 不限制播放列表中的切片数量
                "-hls_segment_filename", outputPath + "/segment_%03d.ts",  // 设置切片文件命名规则
                outputPath + "/index.m3u8"                // 输出主播放列表文件
        );
        // 执行命令并返回结果
        return executeCommand(builder);
    }


    @Override
    public boolean generateThumbnail(String inputPath, String outputPath, int time) {
        // 设置 FFmpeg 命令，截取视频某一时间点的帧
        ProcessBuilder builder = new ProcessBuilder(
                FFMPEG_PATH, "-i", inputPath,   // 输入文件路径
                "-ss", String.valueOf(time),   // 设置时间点
                "-vframes", "1",               // 只提取一帧
                outputPath                     // 输出截图路径
        );
        // 执行命令并返回结果
        return executeCommand(builder);
    }


    @Override
    public boolean executeCommand(ProcessBuilder builder) {
        builder.redirectErrorStream(true); // 将标准错误流与标准输出流合并，方便调试
        try {
            // 启动 FFmpeg 进程
            Process process = builder.start();
            // 等待进程执行完毕并获取退出码
            int exitCode = process.waitFor();
            // 如果退出码为 0，表示执行成功
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            // 如果执行过程出错，打印错误信息并返回 false
            log.error("视频转换错误", e);
            return false;
        }
    }
}
