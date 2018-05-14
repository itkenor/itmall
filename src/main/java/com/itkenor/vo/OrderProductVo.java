package com.itkenor.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @PACKAGE_NAME:com.itkenor.vo
 * @Auther: itkenor
 * @Date: 2018/5/6 22:36
 * @Description:
 */
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHostName;

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getImageHostName() {
        return imageHostName;
    }

    public void setImageHostName(String imageHostName) {
        this.imageHostName = imageHostName;
    }
}
