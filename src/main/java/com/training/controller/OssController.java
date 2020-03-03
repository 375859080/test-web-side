package com.training.controller;

import com.training.dto.OssCallbackResult;
import com.training.dto.OssPolicyResult;
import com.training.response.ResponseResult;
import com.training.service.Impl.OssServiceImpl;
import com.training.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value="/aliyun/oss",tags="Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation("获取上传文件所需签名")
    @GetMapping("/policy")
    public ResponseResult policy() {
        OssPolicyResult result = ossService.policy();
        return new ResponseResult(result);
    }

    @ApiOperation("oss上传成功回调")
    @PostMapping("/callback")
    public ResponseResult callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return new ResponseResult(ossCallbackResult);
    }

}
