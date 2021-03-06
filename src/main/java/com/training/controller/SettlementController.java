package com.training.controller;


import com.training.domain.Settlement;
import com.training.domain.User;
import com.training.response.ResponseResult;
import com.training.service.SettlementService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(value="/Settlement",tags="用于测试结算表相关接口")
@RequestMapping("/Settlement")
@RestController
public class SettlementController {
    @Autowired
    SettlementService settlementService;

    @ApiResponses({
            @ApiResponse(code=200,message="ok"),
            @ApiResponse(code=500,message="结算不存在"),
            @ApiResponse(code=501,message="id不存在"),
            @ApiResponse(code=502,message="routeid不存在"),
            @ApiResponse(code=503,message="userId不存在"),
            @ApiResponse(code=504,message="secrouteid不存在"),
            @ApiResponse(code=505,message="插入失败"),
            @ApiResponse(code=506,message="更新失败")
    })
    @ApiOperation("查询结算表所有信息")
    @GetMapping("/")
    public ResponseResult findAllSettlement() {
        return settlementService.findAllSettlement();
    }

    @ApiOperation("根据结算id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "报销表id")
    })
    @GetMapping("/id")
    public ResponseResult findSettlementById(@RequestParam("id")Long id) {
        return settlementService.findSettlementById(id);
    }

    @ApiOperation("根据行程id查询报销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeid", value = "行程id")
    })
    @GetMapping("/routeid")
    public ResponseResult findSettlementByRouteId(@RequestParam("routeid")Long routeid) {
        return settlementService.findSettlementByRouteId(routeid);
    }

    @ApiOperation("根据段行程id查询报销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "secrouteid", value = "段行程id")
    })
    @GetMapping("/secrouteid")
    public ResponseResult findSettlementBySecRouteId(@RequestParam("secrouteid")Long secrouteid) {
        return settlementService.findSettlementBySecRouteId(secrouteid);
    }

    @ApiOperation("新增结算表")
    @PostMapping("/save")
    public ResponseResult saveSettlement(@RequestBody Settlement settlement) {
        return settlementService.saveSettlement(settlement);
    }

    @ApiOperation("更新结算表")
    @PostMapping("/update")
    public ResponseResult updateSettlement(@RequestBody Settlement settlement) {
        return settlementService.updateSettlement(settlement);
    }

    @ApiOperation("返回所有数据、所有结算")
    @GetMapping("/fd")
    public ResponseResult findFDSettlements(){
        return settlementService.findFDSettlements();
    }

}
