package com.shangding.wechat.dao;

import com.shangding.wechat.model.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface CartMapper {

    List<Cart> getAll();

    List<Cart> getByCustomerId(int customerId);

    List<Cart> getByCustomerIdAndIds(@Param("customerId") int customerId, @Param("ids") String ids);

    Cart getByCustomerIdAndGoodId(@Param("customerId") int customerId, @Param("goodId") int goodId);

    int insert(Cart cart);

    int updateById(Cart cart);
}
