package com.iecas.communityfile.service;

import com.iecas.communityfile.pojo.middleEntity.TranscodeResolutionDataAndStatus;

import java.io.IOException;

public interface FfmpegService {


    /**
     * 执行 FFmpeg 多清晰度转码为 HLS
     * @param ffmpegPath FFmpeg 可执行路径，如 "ffmpeg"
     * @param inputPath 输入视频文件路径，如 "/videos/input.mp4"
     * @param outputDir 输出目录路径，如 "/videos/output/"
     */
    TranscodeResolutionDataAndStatus transcodeToHls(String ffmpegPath, String inputPath, String outputDir) throws IOException, InterruptedException;


    /**
     * 视频转码
     * @param outputPath 输出路径
     * @param inputPath 输入路径
     * @throws IOException
     * @throws InterruptedException
     */
    TranscodeResolutionDataAndStatus transcodeToHls(String inputPath, String outputPath) throws IOException, InterruptedException;


    /**
     * 视频转码
     * @param inputPath 原始文件输入路径
     * @throws IOException
     * @throws InterruptedException
     */
    TranscodeResolutionDataAndStatus transcodeToHls(String inputPath) throws IOException, InterruptedException;
}
