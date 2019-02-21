package com.shangding.wechat.model;

import java.math.BigDecimal;

/**
 * @author yjz
 * @since 2019/2/18
 */
public class OrderItem {

    private Integer id;

    private Integer goodId;

    private Integer quantity;

    private Integer orderId;

    private BigDecimal price;

    private BigDecimal totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderItem() {
    }

    public OrderItem(Integer goodId, Integer quantity, Integer orderId, BigDecimal price, BigDecimal totalPrice) {
        this.goodId = goodId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", goodId=" + goodId +
                ", quantity=" + quantity +
                ", orderId=" + orderId +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
