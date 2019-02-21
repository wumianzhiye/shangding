package com.shangding.wechat.controller;

import com.shangding.wechat.facade.CustomerFacade;
import com.shangding.wechat.model.CustomerAddress;
import com.shangding.wechat.service.CustomerAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yjz
 * @since 2019/2/19
 */
@RestController
@RequestMapping("customer/address/")
public class CustomerAddressController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAddressController.class);
    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private CustomerFacade customerFacade;

    /**
     * 新增地址
     * @param code 寻找customer
     * @param name 人名
     * @param phone 电话
     * @param destination 收货地方
     */
    @PostMapping("add")
    public void addAddress(@RequestParam("name") String name, @RequestParam("phone") String phone,
                           @RequestParam("destination") String destination, @RequestParam("code") String code){
        int customerId = this.customerFacade.getCustomerId(code);
        LOGGER.info("add customer address,name:{},phone:{},destination:{},customerId:{}", name, phone, destination, customerId);
        CustomerAddress customerAddress = new CustomerAddress(name, destination, phone, customerId);
        this.customerAddressService.save(customerAddress);
    }
}
