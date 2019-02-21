package com.shangding.wechat.dao;

import com.shangding.wechat.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface OrderMapper {

    List<Order> getAll();

    int insert(Order order);

    List<Order> getByCustomerId(@Param("customerId") int customerId, @Param("maxId") Integer maxId);

   Order getById(int id);

   int updateById(Order order);

   int updateStatusById(@Param("id") int id, @Param("status") int status);
}
