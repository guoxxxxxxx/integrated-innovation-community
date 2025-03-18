package com.iecas.communityfile.controller;


import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.model.file.entity.FileInfo;
import com.iecas.communityfile.dto.FileUploadDTO;
import com.iecas.communityfile.service.FileInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * (UploadInfo)表控制层
 *
 * @author guox
 * @since 2025-02-17 21:29:36
 */
@RestController
@RequestMapping("/upload")
public class UploadController{

    /**
     * 服务对象
     */
    @Autowired
    private FileInfoService fileInfoService;


    @Operation(summary = "上传单个文件")
    @Logger("上传单个文件")
    @PostMapping("/singleFile")
    @Auth()
    public CommonResult singleFile(@ModelAttribute FileUploadDTO dto) throws IOException, NoSuchAlgorithmException {
        FileInfo uploadInfo = fileInfoService.uploadSingleFile(dto);
        return new CommonResult().success().message("上传成功").data(uploadInfo);
    }

}

