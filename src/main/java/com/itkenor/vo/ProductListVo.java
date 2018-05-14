package com.itkenor.vo;

import java.math.BigDecimal;

/**
 * @PACKAGE_NAME:com.itkenor.vo
 * @Auther: itkenor
 * @Date: 2018/4/30 10:32
 * @Description:
 */
public class ProductListVo {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageHostName() {
        return imageHostName;
    }

    public void setImageHostName(String imageHostName) {
        this.imageHostName = imageHostName;
    }

    private Integer id;
    private Integer categoryId;

    private String name;
    private String subtitle;
    private String mainImage;
    private BigDecimal price;
    private Integer status;
    //vo
    private String imageHostName;
}
