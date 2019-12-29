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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(value="/reviewReimburse",tags="报销审核接口")
@RequestMapping("/reviewReimburse")
@RestController
public class ReviewReimburseController {


    @Autowired
    private ReviewReimburseService reviewReimburseService;


    @ApiOperation("同意报销")
    @PostMapping("/")
    public ResponseResult agree(@RequestBody ReviewReimburseDTO reviewReimburseDTO, HttpServletRequest request){

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        return reviewReimburseService.acceptReimburse(reviewReimburseDTO,account);
    }
}