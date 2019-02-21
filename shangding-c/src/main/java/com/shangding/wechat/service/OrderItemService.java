package com.shangding.wechat.service;

import com.shangding.wechat.model.OrderItem;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/18
 */
public interface OrderItemService {

    boolean insert(OrderItem orderItem);

    List<OrderItem> getByOrderId(int orderId);

}
