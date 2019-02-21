package com.shangding.wechat.service;

import com.shangding.wechat.dao.OrderItemMapper;
import com.shangding.wechat.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/18
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public boolean insert(OrderItem orderItem) {
        return this.orderItemMapper.insert(orderItem) > 0;
    }

    @Override
    public List<OrderItem> getByOrderId(int orderId) {
        return this.orderItemMapper.getByOrderId(orderId);
    }
}
