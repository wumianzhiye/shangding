package com.shangding.wechat.model;

import java.util.Date;

/**
 * @author yjz
 * @since 2019/2/19
 */
public class CustomerAddress {

    private int id;

    /**
     * 人名
     */
    private String name;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 电话
     */
    private String phone;

    private int customerId;

    private Date createTime;

    public CustomerAddress() {
    }

    public CustomerAddress(String name, String destination, String phone, int customerId) {
        this.name = name;
        this.destination = destination;
        this.phone = phone;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerAddress{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", phone='" + phone + '\'' +
                ", customerId=" + customerId +
                ", createTime=" + createTime +
                '}';
    }
}
