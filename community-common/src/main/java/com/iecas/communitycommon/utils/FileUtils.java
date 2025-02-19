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

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



@Slf4j
public class FileUtils {


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
    private static String calculateMD5(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
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
     * 文件写入指定文件
     * @param savePath 所要保存的地址
     * @param inputStream   输入流
     */
    public static void write(String savePath, InputStream inputStream) throws IOException{
        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath)){
            byte[] buffer = new byte[1024];
            int len;
            while (-1 != (len = inputStream.read(buffer))){
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
        }
    }
}
