package com.shangding.wechat.facade;

import com.shangding.wechat.model.WxaSessionDto;
import com.shangding.wechat.service.CustomerService;
import com.shangding.wechat.service.WxaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yjz
 * @since 2019/2/17
 */
@Service
public class CustomerFacade {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private WxaUserService wxaUserService;

    /**
     * 微信授权接口拿到customerId
     * @param code
     * @param thumb
     * @param nickName
     * @return
     */
    public int getCustomerId(String code,String thumb, String nickName){
        WxaSessionDto wxaSessionDto = this.wxaUserService.getWxaSessionDto(code);
        int customerId = this.customerService.getOrSaveCustomer(wxaSessionDto.getOpenId(), thumb, nickName);
        return customerId;
    }

    /**
     * 微信登录接口拿到customerId
     * @param code
     * @return
     */
    public int getCustomerId(String code){
        WxaSessionDto wxaSessionDto = this.wxaUserService.getWxaSessionDto(code);
        int customerId = this.customerService.insertCustomerOnlyOpenId(wxaSessionDto.getOpenId());
        return customerId;
    }

}
