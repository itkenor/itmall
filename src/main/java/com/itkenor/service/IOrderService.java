package com.itkenor.service;

import com.github.pagehelper.PageInfo;
import com.itkenor.common.ServerResponse;
import com.itkenor.vo.OrderVo;

import java.util.Map;

/**
 * @PACKAGE_NAME:com.itkenor.service
 * @Auther: itkenor
 * @Date: 2018/5/3 17:24
 * @Description:
 */
public interface IOrderService {
    ServerResponse pay(Integer userId, Long orderNo, String path);
    ServerResponse alipayCallback(Map<String,String> map);
    ServerResponse checkOrderStatus(Integer userId,Long orderNo);
    ServerResponse createOrder(Integer userId,Integer shippingId);
    ServerResponse cancelOrder(Integer userId , Long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse orderDetail(Integer userId , Long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, Integer pageNum, Integer pageSize);
    ServerResponse<PageInfo> manageList(Integer pageNum,Integer pageSize);
    ServerResponse<OrderVo> manageDetail(Long orderNo);
    ServerResponse<PageInfo> manageSearch(Integer pageNum,Integer pageSize,Long orderNo);
    ServerResponse<String> manageSendGoods(Long orderNo);
}
