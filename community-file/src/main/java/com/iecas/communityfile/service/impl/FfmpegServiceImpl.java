/**
 * @Time: 2025/2/17 16:54
 * @Author: guoxun
 * @File: FFmpegServiceImpl
 * @Description:
 */

package com.iecas.communityfile.service.impl;


import com.iecas.communitycommon.utils.FileUtils;
import com.iecas.communitycommon.utils.VideoUtils;
import com.iecas.communityfile.pojo.middleEntity.TranscodeResolutionDataAndStatus;
import com.iecas.communityfile.service.FfmpegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service("ffmpeg-Service")
@Slf4j
public class FfmpegServiceImpl implements FfmpegService {

    @Value("${file.default-output-path}")
    private String defaultOutputPath;

    @Value("${file.default-ffmpeg-path}")
    private String defaultFfmpegPath;


    @Override
    public TranscodeResolutionDataAndStatus transcodeToHls(String inputPath) throws IOException, InterruptedException {
        return transcodeToHls(defaultFfmpegPath, inputPath);
    }


    @Override
    public TranscodeResolutionDataAndStatus transcodeToHls(String inputPath, String outputPath) throws IOException, InterruptedException{
        return transcodeToHls(defaultFfmpegPath, inputPath, outputPath);
    }


    @Override
    public TranscodeResolutionDataAndStatus transcodeToHls(String ffmpegPath, String inputPath, String outputDir) throws IOException, InterruptedException {

        // 设置默认路径
        if (ffmpegPath == null || ffmpegPath.isEmpty()){
            ffmpegPath = defaultFfmpegPath;
        }
        if (outputDir == null || outputDir.isEmpty()){
            outputDir = defaultOutputPath;
        }

        // 确保输出目录存在
        File output = new File(outputDir);
        if (!output.exists()) {
            output.mkdirs();
        }

        // 根据输入路径获取视频文件的uuid
        String[] split = inputPath.split("/");
        String fileUUID = split[split.length - 1].split("\\.")[0];

        // 获取输入视频的分辨率
        int[] videoResolution = VideoUtils.getVideoResolution(defaultFfmpegPath, inputPath);
        int height = videoResolution[0];

        // 输出结果
        TranscodeResolutionDataAndStatus result = new TranscodeResolutionDataAndStatus();
        result.setMaster(FileUtils.pathJoin("/", fileUUID, "master.m3u8"));

        List<String> command = new ArrayList<>();

        command.add(ffmpegPath);
        command.add("-y"); // 覆盖输出
        command.add("-i");
        command.add(inputPath);

        command.add("-preset");
        command.add("veryfast");
        command.add("-g");
        command.add("48");
        command.add("-sc_threshold");
        command.add("0");

        // 360p
        if (height >= 360) {
            command.add("-map");
            command.add("0:v:0");
            command.add("-map");
            command.add("0:a:0");
            command.add("-s:v:0");
            command.add("640x360");
            command.add("-b:v:0");
            command.add("800k");
            command.add("-c:v:0");
            command.add("libx264");
            command.add("-c:a:0");
            command.add("aac");

            // 添加转码后的信息
            result.set_360p(FileUtils.pathJoin("/", fileUUID, "v0", "index.m3u8"));
        }

        // 480p
        if (height >= 480) {
            command.add("-map");
            command.add("0:v:0");
            command.add("-map");
            command.add("0:a:0");
            command.add("-s:v:1");
            command.add("854x480");
            command.add("-b:v:1");
            command.add("1400k");
            command.add("-c:v:1");
            command.add("libx264");
            command.add("-c:a:1");
            command.add("aac");

            // 添加转码后的信息
            result.set_480p(FileUtils.pathJoin("/", fileUUID, "v1", "index.m3u8"));
        }

        // 720p
        if (height >= 720) {
            command.add("-map");
            command.add("0:v:0");
            command.add("-map");
            command.add("0:a:0");
            command.add("-s:v:2");
            command.add("1280x720");
            command.add("-b:v:2");
            command.add("2800k");
            command.add("-c:v:2");
            command.add("libx264");
            command.add("-c:a:2");
            command.add("aac");

            // 添加转码后的信息
            result.set_720p(FileUtils.pathJoin("/", fileUUID, "v2", "index.m3u8"));
        }

        // 1080p
        if (height >= 1080) {
            command.add("-map");
            command.add("0:v:0");
            command.add("-map");
            command.add("0:a:0");
            command.add("-s:v:3");
            command.add("1920x1080");
            command.add("-b:v:3");
            command.add("5000k");
            command.add("-c:v:3");
            command.add("libx264");
            command.add("-c:a:3");
            command.add("aac");

            // 添加转码后的信息
            result.set_1080p(FileUtils.pathJoin("/", fileUUID, "v3", "index.m3u8"));
        }

        // HLS 输出
        command.add("-f");
        command.add("hls");
        command.add("-hls_time");
        command.add("5");
        command.add("-hls_playlist_type");
        command.add("vod");
        command.add("-hls_segment_filename");
        command.add(outputDir + "/v%v/segment_%03d.ts");
        command.add("-master_pl_name");
        command.add("master.m3u8");
        command.add("-var_stream_map");
        command.add("v:0,a:0 v:1,a:1 v:2,a:2 v:3,a:3");
        command.add(outputDir + "/v%v/index.m3u8");

        // 执行命令
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.inheritIO(); // 控制台显示输出
        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg 转码失败，退出码: " + exitCode);
        }
        else {
            result.setOk(true);
            return result;
        }
    }
}
