package com.shangding.wechat.service;

import com.shangding.wechat.model.WxaSessionDto;

/**
 * @author yjz
 * @since 2019/2/16
 */
public interface WxaUserService {

    /**
     * 根据code，去微信接口调取用户信息
     * @param code
     * @return
     */
    WxaSessionDto getWxaSessionDto(String code);

}
