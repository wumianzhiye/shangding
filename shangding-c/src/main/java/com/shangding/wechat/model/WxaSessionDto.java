package com.shangding.wechat.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * @Project : Coffee Box
 * @Description : 小程序会话密钥信息
 * @author : Zhou Yu Cheng
 * @Creation Date : 2017/12/21 15:02
 *  openid	用户唯一标识
    session_key	会话密钥
    unionid	用户在开放平台的唯一标识符。本字段在满足一定条件的情况下才返回。具体参看UnionID机制说明
 */
public class WxaSessionDto extends WechatCommonResultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("openid")
    private String openId;

    @SerializedName("session_key")
    private String sessionKey;

    @SerializedName("unionid")
    private String unionId;

    public String getOpenId() {
        return openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public WxaSessionDto setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public WxaSessionDto setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public WxaSessionDto setUnionId(String unionId) {
        this.unionId = unionId;
        return this;
    }

    @Override
    public String toString() {
        return "WxaSessionDto{" +
                "openId='" + openId + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", unionId='" + unionId + '\'' +
                '}';
    }
}
