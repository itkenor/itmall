package com.itkenor.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @PACKAGE_NAME:com.itkenor.vo
 * @Auther: itkenor
 * @Date: 2018/5/6 19:51
 * @Description:
 */
public class OrderItemVo {
    private long orderNo;
    private Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal currentUniPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String createTime;

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getCurrentUniPrice() {
        return currentUniPrice;
    }

    public void setCurrentUniPrice(BigDecimal currentUniPrice) {
        this.currentUniPrice = currentUniPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
