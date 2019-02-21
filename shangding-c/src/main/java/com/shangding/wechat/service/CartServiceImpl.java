package com.shangding.wechat.service;

import com.shangding.wechat.dao.CartMapper;
import com.shangding.wechat.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> getAll() {
        return this.cartMapper.getAll();
    }

    @Override
    public List<Cart> getByCustomerId(int customerId) {
        return this.cartMapper.getByCustomerId(customerId);
    }

    @Override
    public List<Cart> getByCustomerIdAndIds(int customerId, String ids) {
        return this.cartMapper.getByCustomerIdAndIds(customerId, ids);
    }

    @Override
    public boolean insert(Cart cart) {
        int insert = this.cartMapper.insert(cart);
        return insert > 0;
    }

    @Override
    public boolean save(Cart cart) {
        // 根据customerId和goodId判断是否有，如果有数量加一
        Cart byCustomerIdAndGoodId = this.cartMapper.getByCustomerIdAndGoodId(cart.getCustomerId(), cart.getGoodId());

        // 购物车没有此商品
        if(byCustomerIdAndGoodId == null){
            this.insert(cart);
        } else {
            // 有
            int totalNum = byCustomerIdAndGoodId.getNum() + cart.getNum();
            byCustomerIdAndGoodId.setNum(totalNum);
            this.cartMapper.updateById(byCustomerIdAndGoodId);
        }
        return true;
    }

    @Override
    public boolean updateById(Cart cart) {
        return this.cartMapper.updateById(cart) > 0;
    }
}
