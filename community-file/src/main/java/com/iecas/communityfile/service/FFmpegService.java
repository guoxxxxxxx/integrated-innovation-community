package com.iecas.communityfile.service;

public interface FFmpegService {

    /**
     * 转换视频格式
     * 通过使用ffmpeg将输入视频文件转化为目标格式
     * @param inputPath 输入视频的文件路径
     * @param outputPath 输出视频文件的路径
     * @return true: 转换成果; false: 转换失败
     */
    boolean convertVideo(String inputPath, String outputPath);


    /**
     * 生成 HLS 视频切片
     * 使用 FFmpeg 将视频切割成多个 .ts 片段, 并生成 HLS 主播放列表
     * @param inputPath 输入视频文件路径
     * @param outputPath 输出目录
     * @return true: 转换成果; false: 转换失败
     */
    boolean generateHLS(String inputPath, String outputPath);


    /**
     * 生成视频截图
     * 使用 FFmpeg 截取视频中的某个时间点的帧并保存为图像文件。
     *
     * @param inputPath 输入视频文件的路径
     * @param outputPath 截图保存路径
     * @param time 截图的时间点（单位：秒）
     * @return 返回 true 表示截图生成成功，false 表示失败
     */
    boolean generateThumbnail(String inputPath, String outputPath, int time);


    /**
     * 执行 FFmpeg 命令
     * 此方法用来启动 FFmpeg 进程并等待其执行结果，判断命令是否成功执行。
     *
     * @param builder 构建的 ProcessBuilder 对象，包含 FFmpeg 命令
     * @return 返回 true 表示命令成功执行，false 表示执行失败
     */
    boolean executeCommand(ProcessBuilder builder);
}
