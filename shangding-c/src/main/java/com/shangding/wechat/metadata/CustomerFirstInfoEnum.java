package com.shangding.wechat.metadata;

/**
 * @author yjz
 * @since 2019/2/18
 */

public enum CustomerFirstInfoEnum {


    NO(1, "不是第一次登陆"),
    YES(0, "是第一次登陆");
    private int code;
    private String value;


    CustomerFirstInfoEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
