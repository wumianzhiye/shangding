package com.shangding.wechat.service;

import com.shangding.wechat.model.Order;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface OrderService {

    List<Order> getAll();

    boolean insert(Order order);

    List<Order> getByCustomerId(int customerId, Integer maxOrderId);

    Order getById(int id);

    /**
     * 先判断此用户是否是订单主人
     * 取消订单，把订单状态变为取消
     * @param orderId
     * @return
     */
    boolean cancelById(int orderId, int customerId);

}
