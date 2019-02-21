package com.shangding.wechat.service;

import com.shangding.wechat.dao.CustomerAddressMapper;
import com.shangding.wechat.model.CustomerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yjz
 * @since 2019/2/19
 */
@Service
public class CustomerAddressServiceImpl implements CustomerAddressService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAddressServiceImpl.class);

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Override
    public boolean save(CustomerAddress customerAddress) {
        return this.customerAddressMapper.insert(customerAddress) > 0;
    }
}
