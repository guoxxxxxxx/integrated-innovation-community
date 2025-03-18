/**
 * @Time: 2025/3/17 10:37
 * @Author: guoxun
 * @File: ShaUtils
 * @Description: 计算文件md5
 */

package com.iecas.communitycommon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ShaUtils {


    /**
     * 计算文件的哈希值
     * @param inputStream 文件的输入流
     * @return {@link String} SHA
     * @throws IOException, NoSuchAlgorithmException
     */
    public static String getFileSHA(InputStream inputStream, String algorithm) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
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
     * 计算文件的md5
     * @param inputStream 文件的输入流
     * @return {@link String} md5
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileMd5(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        return getFileSHA(inputStream, "MD5");
    }


    /**
     * 计算文件的sha256
     * @param inputStream 文件的输入流
     * @return {@link String} sha256
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileSHA256(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        return getFileSHA(inputStream, "SHA-256");
    }


    /**
     * 计算文件的sha512
     * @param inputStream 文件的输入流
     * @return {@link String} sha512
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileSHA512(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        return getFileSHA(inputStream, "SHA-512");
    }
}
