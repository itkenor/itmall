package com.itkenor.service;

import com.itkenor.common.ServerResponse;
import com.itkenor.vo.CartVo;

/**
 * @PACKAGE_NAME:com.itkenor.service
 * @Auther: itkenor
 * @Date: 2018/4/30 23:28
 * @Description:
 */
public interface ICartService {
    ServerResponse<CartVo> addProductToCart(Integer userId, Integer productId, Integer checkCount);
    ServerResponse<CartVo> updateProductToCart(Integer userId,Integer productId,Integer checkCount);
    ServerResponse<CartVo> deleteProductToCart(Integer userId,String productIds);
    ServerResponse<CartVo> selectProductToCart(Integer userId);
    ServerResponse<CartVo> selectOrUnselect(Integer checked,Integer userId,Integer productId);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
