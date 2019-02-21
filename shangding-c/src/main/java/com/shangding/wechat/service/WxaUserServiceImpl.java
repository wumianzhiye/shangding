package com.shangding.wechat.service;

import com.google.gson.Gson;
import com.shangding.wechat.model.WxaSessionDto;
import com.shangding.wechat.utils.HttpsClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yjz
 * @since 2019/2/16
 */
@Service
public class WxaUserServiceImpl implements WxaUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxaUserServiceImpl.class);

    private static Gson GSON = new Gson();

    @Autowired
    private HttpsClientService httpsClientService;

    /**
     * 小程序登录获得js_code,然后获取openId信息
     */
    private static final String OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    public static final String SECRET = "e54abdf16770c99606594fe1325d10e2";
    public static final String APPID = "wxff9aa985c9470b1a";


    @Override
    public WxaSessionDto getWxaSessionDto(String code) {
        String url = String.format(OPENID_URL, APPID, SECRET, code);
        WxaSessionDto wechatUserSessionDto = null;
        String body = httpsClientService.get(url);
        wechatUserSessionDto = GSON.fromJson(body, WxaSessionDto.class);
        LOGGER.info("getWxaSessionDto : {}, url : {}", wechatUserSessionDto, url);
        return wechatUserSessionDto;
    }
}
