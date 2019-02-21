package com.shangding.wechat.metadata;

/**
 * 购物车是否结算了
 * @author yjz
 * @since 2019/2/18
 */
public enum CartActiveEnum {

    NO(0, "没有结算"),
    YES(1, "已经结算");

    private int code;
    private String value;

    CartActiveEnum(int code, String value) {
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
