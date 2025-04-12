package com.iecas.communityfile.controller;


import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.model.file.entity.FileInfo;
import com.iecas.communityfile.pojo.dto.FileUploadDTO;
import com.iecas.communityfile.pojo.dto.FileUploadMultiBlockDTO;
import com.iecas.communityfile.pojo.dto.FileUploadPreHandleDTO;
import com.iecas.communityfile.pojo.vo.CheckFileUploadIsOkVO;
import com.iecas.communityfile.pojo.vo.FileUploadPreHandleVO;
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


    @Auth
    @Logger("文件分块上传预处理")
    @PostMapping("/fileuploadPreHandle")
    @Operation(summary = "文件分块上传预处理")
    public CommonResult fileuploadPreHandle(@RequestBody FileUploadPreHandleDTO dto) throws IOException {
        FileUploadPreHandleVO vo = fileInfoService.fileuploadPreHandle(dto);
        return new CommonResult().success().data(vo).message("调用成功");
    }


    @Auth
    @PostMapping("/uploadFileMultiBlock")
    @Logger("分块上传文件")
    @Operation(summary = "分块上传文件")
    public CommonResult uploadFileMultiBlock(@ModelAttribute FileUploadMultiBlockDTO dto) throws Exception {
        boolean status = fileInfoService.uploadFileMultiBlock(dto);
        return new CommonResult().success().message("上传成功").data(status);
    }



    @Auth
    @Logger("查询当前文件是否上传成功")
    @GetMapping("/checkFileUploadIsOk")
    @Operation(summary = "查询当前文件是否上传成功")
    public CommonResult checkFileUploadIsOk(@RequestParam String fileUUID) throws IOException, NoSuchAlgorithmException {
        CheckFileUploadIsOkVO result = fileInfoService.checkFileUploadIsOk(fileUUID);
        return new CommonResult().success().data(result);
    }

}

