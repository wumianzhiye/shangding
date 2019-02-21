package com.shangding.wechat.dto;

import java.math.BigDecimal;

/**
 * @author yjz
 * @since 2019/2/18
 */
public class GoodDto {

    private Integer id;

    private String goodName;
    /**
     * 价格
     */
    private BigDecimal goodPrice;
    /**
     * 数量
     */
    private Integer num;

    /**
     * 图片url
     */
    private String goodMainUrl;

    /**
     * 总价
     */
    private Double finalPrice;


    public GoodDto(Integer id, String goodName, BigDecimal goodPrice, Integer num, String goodMainUrl) {
        this.id = id;
        this.goodName = goodName;
        this.goodPrice = goodPrice;
        this.num = num;
        this.goodMainUrl = goodMainUrl;
        // 总价等于单价乘以数量
        this.finalPrice = goodPrice.doubleValue() * num.intValue();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getGoodMainUrl() {
        return goodMainUrl;
    }

    public void setGoodMainUrl(String goodMainUrl) {
        this.goodMainUrl = goodMainUrl;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return "GoodDto{" +
                "id=" + id +
                ", goodPrice=" + goodPrice +
                ", num=" + num +
                ", goodMainUrl='" + goodMainUrl + '\'' +
                ", finalPrice=" + finalPrice +
                '}';
    }
}
