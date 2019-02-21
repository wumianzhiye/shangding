package com.shangding.wechat.dao;

import com.shangding.wechat.model.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface OrderItemMapper {

    int insert(OrderItem orderItem);

    List<OrderItem> getByOrderId(@Param("orderId") int orderId);

}
