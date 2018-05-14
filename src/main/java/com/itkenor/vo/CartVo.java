package com.itkenor.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @PACKAGE_NAME:com.itkenor.vo
 * @Auther: itkenor
 * @Date: 2018/5/1 09:56
 * @Description: 购物车所有商品的展示
 */
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;
    private String imageHostName;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHostName() {
        return imageHostName;
    }

    public void setImageHostName(String imageHostName) {
        this.imageHostName = imageHostName;
    }


}
