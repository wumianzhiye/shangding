package com.shangding.wechat.controller;

import com.shangding.wechat.model.WxaSessionDto;
import com.shangding.wechat.service.CustomerService;
import com.shangding.wechat.service.WxaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yjz
 * @since 2019/2/16
 */
@RestController
@RequestMapping("/user")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private WxaUserService wxaUserService;

    @Autowired
    private CustomerService customerService;

    /**
     * 微信拿到用户信息登录接口
     * @param code 小程序登录微信返回的code
     * @param thumb 微信头像url
     * @param nickName 微信昵称
     */
    @GetMapping("get_user_info")
    public void login(@RequestParam("code") String code,
                      @RequestParam("thumb") String thumb,
                      @RequestParam("nickName") String nickName){
        WxaSessionDto wxaSessionDto = wxaUserService.getWxaSessionDto(code);
        LOGGER.info("get_user_info,code:{},thumb:{},nickName:{},wxaSessionDto:{}", code, thumb, nickName, wxaSessionDto);
        this.customerService.getOrSaveCustomer(wxaSessionDto.getOpenId(), thumb, nickName);
    }

    /**
     * 微信登录接口,这个是不需要用户授权的
     * @param code 小程序登录微信返回的code
     */
    @GetMapping("wx_login")
    public void login(@RequestParam("code") String code){
        LOGGER.info("wx_login,code:{}", code);
        WxaSessionDto wxaSessionDto = wxaUserService.getWxaSessionDto(code);
        LOGGER.info("wx_login,wxaSessionDto:{}", wxaSessionDto);
        this.customerService.insertCustomerOnlyOpenId(wxaSessionDto.getOpenId());
    }

}
