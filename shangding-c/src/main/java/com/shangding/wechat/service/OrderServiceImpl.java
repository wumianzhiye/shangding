package com.shangding.wechat.service;

import com.shangding.wechat.dao.OrderMapper;
import com.shangding.wechat.metadata.OrderStatusEnum;
import com.shangding.wechat.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
@Service
public class OrderServiceImpl implements OrderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getAll() {
        return this.orderMapper.getAll();
    }

    @Override
    public boolean insert(Order order) {
        return this.orderMapper.insert(order) > 0;
    }

    @Override
    public List<Order> getByCustomerId(int customerId, Integer maxOrderId) {
        if(maxOrderId == null || maxOrderId == 0){
            return this.orderMapper.getByCustomerId(customerId, null);
        } else {
            return this.orderMapper.getByCustomerId(customerId, maxOrderId);
        }
    }

    @Override
    public Order getById(int id) {
        return this.orderMapper.getById(id);
    }

    @Override
    public boolean cancelById(int orderId, int customerId) {
        // 找到订单，判断是否是这个用户的
        Order byId = this.getById(orderId);
        if(byId.getCustomerId() != customerId) {
            LOGGER.info("cancel has problem,customer is not owner,orderId:{},customerId:{}", orderId, customerId);
            return false;
        }
        if(byId.getStatus() >= OrderStatusEnum.PAY_FINISH.getCode()){
            LOGGER.info("cancel has problem,order status has problem,orderId:{},customerId:{},status:{}",
                    orderId, customerId, byId.getStatus());
            return false;
        }
        return this.orderMapper.updateStatusById(byId.getId(), OrderStatusEnum.CANCEL.getCode()) > 0;
    }

}
