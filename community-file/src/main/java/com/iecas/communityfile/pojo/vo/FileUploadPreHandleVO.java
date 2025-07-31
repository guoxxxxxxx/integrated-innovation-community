/**
 * @Time: 2025/3/19 15:34
 * @Author: guoxun
 * @File: FileuploadPreHandleVO
 * @Description:
 */

package com.iecas.communityfile.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iecas.communitycommon.utils.FileUtils;
import com.iecas.communityfile.pojo.middleEntity.UploadOtherInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
public class FileUploadPreHandleVO {


    public FileUploadPreHandleVO(Integer chunkCount){
        this.fileChunkInfoList = new ArrayList<>();
        this.chunkCount = chunkCount;
        this.fileUUID = UUID.randomUUID().toString();
        this.initChunk(chunkCount);
    }


    /**
     * 文件的uuid
     */
    private String fileUUID;

    /**
     * 文件的MD5码
     */
    private String md5;

    /**
     * 文件分块数量
     */
    private Integer chunkCount;

    /**
     * 文件分块信息
     */
    private List<FileChunkInfo> fileChunkInfoList;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件原始名称
     */
    private String originFilename;

    /**
     * 文件保存路径
     */
    private String savePath;

    /**
     * 其他信息
     */
    private UploadOtherInfo otherInfo;

    /**
     * 文件上传记录id
     */
    private Long recordId;


    /**
     * 文件块信息对象
     */
    @Data
    public static class FileChunkInfo{

        public FileChunkInfo(Integer chunkId, Boolean isOk){
            this.chunkId = chunkId;
            this.isOk = isOk;
        }

        // 文件块的编号
        Integer chunkId;

        // 文件块是否上传成功
        Boolean isOk;
    }


    /**
     * 初始化文件块信息
     * @param chunkCount 文件块数量
     */
    private void initChunk(Integer chunkCount){
        for (int i=0; i<chunkCount; i++){
            this.fileChunkInfoList.add(new FileChunkInfo(i, false));
        }
    }


    /**
     * 获取文件新名称
     * @return 文件新名称
     */
    public String getNewFilename(){
        return this.fileUUID + "." + this.type;
    }


    /**
     * 获取失败的文件块集合
     * @return 失败的文件块集合
     */
    @JsonIgnore
    public List<Integer> getFailChunksList(){
        List<Integer> failList = new ArrayList<>();
        for (FileChunkInfo e : this.fileChunkInfoList){
            if (!e.getIsOk()){
                failList.add(e.getChunkId());
            }
        }
        return failList;
    }


    /**
     * 检查文件块是否都上传成功
     * @return true: 都上传成功; false: 未上传成功
     */
    public Boolean checkIsOk(){
        // 1. 检查文件块是否都上传成功
        for (FileChunkInfo e : this.fileChunkInfoList){
            if (!e.getIsOk()) return false;
        }
        return true;
    }


    /**
     * 恢复文件类型
     * @return true: 恢复成功; false: 恢复失败
     */
    public Boolean resumeFileType(){
        // 将指定路径下的文件的尾缀修改为type类型
        this.savePath = FileUtils.resumeFileType(this.savePath, this.type);
        return true;
    }
}
