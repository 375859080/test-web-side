package com.training.dto;


/**
 * 消息队列枚举配置
 */
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ROUTE_CANCEL("cancelRoute.direct", "cancelRoute", "cancelRoute"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ROUTE_CANCEL("cancelRoute.direct.ttl", "cancelRoute.ttl", "cancelRoute.ttl");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

    public String getExchange() {
        return exchange;
    }

    public String getName() {
        return name;
    }

    public String getRouteKey() {
        return routeKey;
    }
}
