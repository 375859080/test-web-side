package com.training.component;

import com.training.dto.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消行程消息的发出者
 */
@Component
public class CancelRouteSender {
    private static Logger LOGGER =LoggerFactory.getLogger(CancelRouteSender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long routeId,final long delayTimes){//单位毫秒
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ROUTE_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ROUTE_CANCEL.getRouteKey(), routeId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        LOGGER.info("发送延迟取消的消息routeId:{}",routeId);
    }
}

