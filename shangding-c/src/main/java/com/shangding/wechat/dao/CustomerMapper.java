package com.shangding.wechat.dao;

import com.shangding.wechat.model.Customer;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface CustomerMapper {

    Customer getById(int id);

    Customer getByOpenId(String openId);

    int update(Customer customer);

    int insertCustomer(Customer customer);

    int insertCustomerOnlyOpenId(Customer customer);
}
