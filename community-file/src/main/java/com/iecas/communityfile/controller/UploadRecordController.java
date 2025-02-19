package com.iecas.communityfile.controller;


import com.iecas.communityfile.service.UploadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * (UploadRecord)表控制层
 *
 * @author guox
 * @since 2025-02-19 17:25:27
 */
@RestController
@RequestMapping("uploadRecord")
public class UploadRecordController {

    /**
     * 服务对象
     */
    @Autowired
    private UploadRecordService uploadRecordService;
}

