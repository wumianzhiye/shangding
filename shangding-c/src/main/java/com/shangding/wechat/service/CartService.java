package com.shangding.wechat.service;

import com.shangding.wechat.model.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface CartService {

    List<Cart> getAll();

    List<Cart> getByCustomerId(int customerId);

    List<Cart> getByCustomerIdAndIds(int customerId, String ids);

    boolean insert(Cart cart);

    boolean save(Cart cart);

    boolean updateById(Cart cart);
}
