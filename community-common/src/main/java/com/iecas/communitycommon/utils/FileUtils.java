/**
 * @Time: 2025/2/17 21:48
 * @Author: guoxun
 * @File: MD5Utils
 * @Description:
 */

package com.iecas.communitycommon.utils;

import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



@Slf4j
public class FileUtils {

    /**
     * 缓存块的后缀
     */
    private final static String CACHE_REAR =  ".iic_cache_tmp";


    /**
     * 创建文件夹
     * @param path 所要创建的目录
     * @return boolean创建的成功与否
     */
    public static boolean mkdir(String path){
        if (path == null || path.trim().isEmpty()) {
            return false; // 路径为空
        }

        File dir = new File(path);
        if (dir.exists()) {
            return dir.isDirectory(); // 如果存在但不是文件夹，就返回 false
        }

        return dir.mkdirs(); // 创建多级目录
    }


    /**
     * 保存所要上传的文件 需验证md5
     * @param savePath 文件保存路径
     * @param md5 文件的md5码
     * @param inputStream 文件输入流
     * @return true: 保存成功; false: 保存失败;
     */
    public static boolean saveFile(String savePath, String md5, InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        if (!md5.equals(calculateMD5(inputStream))){
            throw new CommonException("当前文件损坏, 请重新上传");
        }
        else {
            try {
                write(savePath, inputStream);
                return true;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                Files.deleteIfExists(Paths.get(savePath));
                return false;
            }
        }
    }


    /**
     * 保存所要上传的文件
     * @param savePath 文件保存路径
     * @param inputStream 文件输入流
     * @return true: 保存成功; false: 保存失败;
     */
    public static boolean saveFile(String savePath, InputStream inputStream) {
        try {
            write(savePath, inputStream);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            try {
                Files.deleteIfExists(Paths.get(savePath));
            } catch (IOException e1) {
                log.error(e1.getMessage(), e1);
            }
            return false;
        }
    }


    /**
     * 将inputStream 转化为byte
     * @param inputStream 输入流
     * @return {@link byte[]}
     */
    private static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1){
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }


    /**
     * 计算文件的MD5
     * @param inputStream 文件的输入流
     * @return {@link String} md5
     * @throws IOException, NoSuchAlgorithmException
     */
    public static String calculateMD5(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[1024];
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            md.update(buffer, 0, read);
        }
        byte[] md5Bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : md5Bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    /**
     * 计算文件的MD5
     * @param filePath 文件路径
     * @return {@link String} md5
     */
    public static String calculateMD5(String filePath){
        try (FileInputStream fileInputStream = new FileInputStream(filePath)){
            return calculateMD5(fileInputStream);
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("md5校验失败");
        }
    }


    /**
     * 文件写入指定文件
     * @param savePath 所要保存的地址
     * @param inputStream   输入流
     */
    public static void write(String savePath, InputStream inputStream) throws IOException{
        // 提取文件的名称
        String[] split = savePath.split("/");
        String fileName = split[split.length - 1];
        String dirs = savePath.replace("/" + fileName, "");
        mkdir(dirs);
        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath)){
            byte[] buffer = new byte[1024];
            int len;
            while (-1 != (len = inputStream.read(buffer))){
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
        }
    }


    /**
     * 申请保存文件所需的磁盘空间
     * @param savePath 文件保存路径
     * @param fileUUID 文件的uuid
     * @param fileSize 文件大小
     * @return 所申请的临时文件的名称
     */
    public static String applyingDiskSpace(String savePath, String fileUUID, Long fileSize) throws IOException{
        String cacheFilename = savePath + fileUUID + CACHE_REAR;
        try (RandomAccessFile raf = new RandomAccessFile(cacheFilename, "rw")){
            raf.setLength(fileSize);
        }
        return cacheFilename;
    }


    /**
     * 分块写入数据 不能直接调用该方法，调用该方法前，请先调用applyingDiskSpace方法申请空间
     * @param filepath 缓存块路径，即applyingDiskSpace方法的缓存块存放路径
     * @param inputStream 输入流
     * @param chunkMd5 块md5
     * @param chunkId 块id
     * @param chunkSize 块大小
     * @return 是否保存成功
     * @throws Exception error
     */
    public static Boolean writeWithChunk(String filepath, InputStream inputStream, String chunkMd5,
                                         Integer chunkId, Long chunkSize)
            throws Exception {
        byte[] byteArray = toByteArray(inputStream);
        if (checkFileMd5(new ByteArrayInputStream(byteArray), chunkMd5)){
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(filepath, "rw")){
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
                randomAccessFile.seek(chunkId * chunkSize);
                byte[] buffer = new byte[1024];
                int len;
                while (-1 != (len = byteArrayInputStream.read(buffer))){
                    randomAccessFile.write(buffer, 0, len);
                }
            }
            return true;
        }
        else {
            Files.deleteIfExists(Paths.get(filepath));
            throw new RuntimeException("md5码校验未通过, 请重新上传文件");
        }
    }


    /**
     * md5校验
     * @param fileInputStream 文件输入流
     * @param md5 md5
     * @return true: 校验成功; false: 校验失败;
     * @throws Exception error
     */
    public static Boolean checkFileMd5(InputStream fileInputStream, String md5) throws Exception{
        String calculateMD5 = calculateMD5(fileInputStream);
        return calculateMD5.equals(md5);
    }


    /**
     * 重置文件类型
     * @param filePath 文件路径
     * @param originType 原始文件类型
     * @return 新的文件路径
     */
    public static String resumeFileType(String filePath, String originType) {
        File oldFile = new File(filePath);
        String fileName = oldFile.getName();
        int lastDotIndex = fileName.lastIndexOf(".");
        String newFilename = fileName.substring(0, lastDotIndex) + "." + originType;

        File newFile = new File(oldFile.getParent(), newFilename);

        boolean renamed = oldFile.renameTo(newFile);
        if (!renamed) {
            throw new RuntimeException("文件重命名失败");
        }

        return newFile.getAbsolutePath();
    }


    /**
     * 放照python中的OS.path.join方法构造的路径拼接器
     * @param args 所要拼接的路径参数
     * @return 拼接完成的路径参数
     */
    public static String pathJoin(String ... args){
        String SPLIT = "/";
        StringBuilder result = new StringBuilder();
        for(int i=0; i<args.length; i++){
            result.append(args[i]);
            if (i != args.length - 1){
                result.append(SPLIT);
            }
        }
        return result.toString();
    }
}
