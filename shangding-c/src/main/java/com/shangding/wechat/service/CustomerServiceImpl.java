package com.shangding.wechat.service;

import com.shangding.wechat.dao.CustomerMapper;
import com.shangding.wechat.metadata.CustomerFirstInfoEnum;
import com.shangding.wechat.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yjz
 * @since 2019/2/16
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public int getOrSaveCustomer(String openId, String thumb, String nickName) {
        Customer byOpenId = this.customerMapper.getByOpenId(openId);
        if (byOpenId == null) {
            // 新用户
            Customer customer = new Customer(openId, thumb, nickName);
            this.customerMapper.insertCustomer(customer);
            return customer.getId();
        } else if(byOpenId.getFirstGetInfo() == CustomerFirstInfoEnum.YES.getCode()){
            // 第一次拿到用户信息
            Customer customer = new Customer(byOpenId.getId(), openId, thumb, nickName, CustomerFirstInfoEnum.NO.getCode(),new Date());
            this.customerMapper.update(customer);
        } else if(System.currentTimeMillis() - byOpenId.getUpdateTime().getTime() > 15 * 60 * 1000){
            // 超过15分钟更新用户
            Customer customer = new Customer(byOpenId.getId(), openId, thumb, nickName, new Date());
            this.customerMapper.update(customer);
        }
        return byOpenId.getId();
    }

    @Override
    public int insertCustomerOnlyOpenId(String openId) {
        Customer byOpenId = this.customerMapper.getByOpenId(openId);
        if (byOpenId == null) {
            // 第一次来的用户
            Customer customer = new Customer(openId, CustomerFirstInfoEnum.YES.getCode());
            this.customerMapper.insertCustomerOnlyOpenId(customer);
            return customer.getId();
        } else if(System.currentTimeMillis() - byOpenId.getUpdateTime().getTime() > 15 * 60 * 1000){
            // 超过15分钟更新用户
            Customer customer = new Customer(openId, CustomerFirstInfoEnum.YES.getCode(), new Date());
            this.customerMapper.update(customer);
        }
        return byOpenId.getId();
    }

}
