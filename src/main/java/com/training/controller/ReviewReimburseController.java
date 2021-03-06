package com.training.controller;


import com.training.domain.Account;
import com.training.domain.Master;
import com.training.domain.User;
import com.training.dto.ReviewReimburseDTO;
import com.training.response.ResponseResult;
import com.training.service.AccountService;
import com.training.service.ReimburseService;
import com.training.service.ReviewReimburseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(value = "/reviewReimburse", tags = "报销审核接口")
@RequestMapping("/reviewReimburse")
@RestController
public class ReviewReimburseController {


    @Autowired
    private ReviewReimburseService reviewReimburseService;


    @ApiOperation("同意报销")
    @PostMapping("/")
    public ResponseResult agree(@RequestBody ReviewReimburseDTO reviewReimburseDTO, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Master master = (Master) session.getAttribute("master");
        if (master == null) return new ResponseResult(500, "操作失败，管理员信息获取失败，请检查账户");
        return reviewReimburseService.acceptReimburse(reviewReimburseDTO, master);
    }

    @ApiOperation("拒绝报销")
    @PostMapping("/reject")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 500, message = "操作失败，管理员信息获取失败，请检查账户"),
            @ApiResponse(code = 501, message = "相应路程不存在"),
            @ApiResponse(code = 502, message = "message为空")
    })
    public ResponseResult reject(@RequestBody ReviewReimburseDTO reviewReimburseDTO, HttpServletRequest request) {
        if (reviewReimburseDTO.getMessage() == null || reviewReimburseDTO.getMessage().equals(""))
            return new ResponseResult(502, "message为空");
        HttpSession session = request.getSession();
        Master master = (Master) session.getAttribute("master");
        if (master == null) return new ResponseResult(500, "操作失败，管理员信息获取失败，请检查账户");
        return reviewReimburseService.rejectReimburse(reviewReimburseDTO, master);
    }
}
