package com.shangding.wechat.metadata;

/**
 * 订单状态
 * @author yjz
 * @since 2019/2/18
 */
public enum OrderStatusEnum {

    WAIT_PAY(1, "等待支付"),

    PAY_FINISH(3 , "支付完成"),

    DELIVERED(4, "已发货"),

    FINISH(100, "已完成"),

    CANCEL(101, "已取消");

    private int code;

    private String value;

    OrderStatusEnum(int code, String value) {
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
