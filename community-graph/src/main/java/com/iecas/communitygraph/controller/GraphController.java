package com.iecas.communitygraph.controller;


import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
public class GraphController {


    @Logger("加载图片")
    @RequestMapping("")
    public CommonResult loadGraph(){

        return new CommonResult();
    }
}
