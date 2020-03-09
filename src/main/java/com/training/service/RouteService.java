package com.training.service;

import com.training.domain.Route;
import com.training.model.RouteModel;
import com.training.response.ResponseResult;

import java.text.ParseException;
import java.util.List;

public interface RouteService {
    ResponseResult findAllRoute();
    ResponseResult findRouteById(Long id);
    ResponseResult findRouteByCarId(Long carid);
    ResponseResult findRouteByUserId(Long userId);
    //根据UserId返回路程的详细信息
    void deleteRoute(Route route);
    void deleteRouteById(Long id);
    void deleteRouteByCarId(Long carid);
    void deleteRouteByUserId(Long userId);
    ResponseResult saveRoute(Route route);
    ResponseResult updateRoute(Route route);
    ResponseResult updateStatusOfRouteById(Long id, int st);
    // 根据审核状态返回行程
    ResponseResult findRoutesByStatus(int status);
    // 根据审核状态和管理员id返回行程
    ResponseResult findRoutesByStatusAndMasterId(int status,Long masterId);

    // 获取包含所有信息的所有路程
    ResponseResult findFDRoutes();
    // 根据id获取包含所有信息的路程
    RouteModel findFDRouteById(Long id);
    // 根据UserId获取该用户车辆的行程信息
    ResponseResult findCarRouteByUserId(Long userId);
    //根据报销状态返回行程
    ResponseResult findFDRoutesByIsReimburse(int is);
    //根据审核状态和用户Id返回包含所有信息的路程
    ResponseResult findDataByUserIdAndStatus(Long userId);
    //开始行程
    ResponseResult startRoute(Long UserId,Long RouteId,Long secRouteId,String trid,String carStartTime);
    //结束行程
    ResponseResult stopRoute(Long UserId,Long RouteId,Long secRouteId,Double plannedDistance,Double actualDistance) throws ParseException;
    //返回当前登录用户的未报销行程
    ResponseResult findUserRouteByStatus(Long userId,int status,int isReimburse);
    //根据行程完成情况和报销情况和公司id返回行程
    ResponseResult findRoutesByStatusAndIsReimburseAndCompanyId(int status,int isReimburse,Long companyId);
    //返回用户的所有行程信息
    ResponseResult findRoutesByUserId(Long userId);
    //批量修改route的isReimburse字段，改变审核状态
    ResponseResult updateRoutesIsReimburseByIds(List<Long>ids,int status);
    //自动取消超时行程
    void cancelTimeOutRoute();
}
