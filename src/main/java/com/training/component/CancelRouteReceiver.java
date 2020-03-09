package com.training.component;

import com.training.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消行程消息的处理者
 */
@Component
@RabbitListener(queues = "cancelRoute")
public class CancelRouteReceiver {
    private static Logger LOGGER =LoggerFactory.getLogger(CancelRouteReceiver.class);
    @Autowired
    private RouteService routeService;
    @RabbitHandler
    public void handle(Long routeId){
        LOGGER.info("收到延迟取消的消息routeId:{}",routeId);
        routeService.updateStatusOfRouteById(routeId,4);
    }
}

