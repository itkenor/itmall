package com.itkenor.service;

import com.github.pagehelper.PageInfo;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.Product;
import com.itkenor.vo.ProductDetailVo;

/**
 * @PACKAGE_NAME:com.itkenor.service.impl
 * @Auther: itkenor
 * @Date: 2018/4/29 21:23
 * @Description:
 */
public interface IProductService {
    ServerResponse saveORUpdateProduct(Product product);
    ServerResponse setSaleStatus(Integer productId,Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize);
    ServerResponse<PageInfo> searchProductList(String productName ,Integer productId,Integer pageNum,Integer pageSize);
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductByKeyWordAndCategoryId(String keyWord,Integer categoryId,Integer pageNum,Integer pageSize,String orderBy);
}
