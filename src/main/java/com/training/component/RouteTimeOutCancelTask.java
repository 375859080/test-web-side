package com.training.component;

import com.training.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class RouteTimeOutCancelTask {
//    private Logger LOGGER = LoggerFactory.getLogger(RouteTimeOutCancelTask.class);
    @Autowired
    RouteService routeService;
    /**
     * 每10分钟扫描一次，扫描超时未出发的行程
     */
    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutRoute() {
        routeService.cancelTimeOutRoute();
//        LOGGER.info("取消超时行程");
    }
}

