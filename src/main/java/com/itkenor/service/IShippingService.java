package com.itkenor.service;

import com.github.pagehelper.PageInfo;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.Shipping;

/**
 * @PACKAGE_NAME:com.itkenor.service
 * @Auther: itkenor
 * @Date: 2018/5/2 21:01
 * @Description:
 */
public interface IShippingService {
    ServerResponse insertShipping(Integer userId, Shipping shipping);
    ServerResponse<String> deleteShipping(Integer userId,Integer shippingId);
    ServerResponse updateShipping(Integer userId,Shipping shipping);
    ServerResponse<Shipping> selectShipping(Integer userId,Integer shippingId);
    ServerResponse<PageInfo> selectAllShipping(Integer userId, Integer pageNum, Integer pageSize);
}
