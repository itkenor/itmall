package com.itkenor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.dao.ShippingMapper;
import com.itkenor.pojo.Shipping;
import com.itkenor.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME:com.itkenor.service.impl
 * @Auther: itkenor
 * @Date: 2018/5/2 21:02
 * @Description:
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 添加收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse insertShipping(Integer userId,Shipping shipping){
        if(shipping == null ){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        }
        shipping.setUserId(userId);
        int resultCount = shippingMapper.insert(shipping);
        if(resultCount == 0){
            return ServerResponse.createByError("添加地址失败");
        }
        Map resultMap = Maps.newHashMap();
        resultMap.put("shippingId",shipping.getId());
        return ServerResponse.createBySuccess("添地址成功",resultMap);
    }

    /**
     * 删除收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<String> deleteShipping(Integer userId,Integer shippingId){
        int resultCount = shippingMapper.deleteByUserIdAndShippingId(userId,shippingId);
        if(resultCount == 0){
            return ServerResponse.createByError("删除地址失败");
        }
        return ServerResponse.createBySuccess("删除址成功");
    }

    /**
     * 更新收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse updateShipping(Integer userId,Shipping shipping){
        if(shipping == null ){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        }
        int resultCount = shippingMapper.updateShippingByUserIdAndShippingId(userId,shipping);
        if(resultCount == 0){
            return ServerResponse.createByError("更新地址失败");
        }
        return ServerResponse.createBySuccessMessage("更新址成功");
    }

    /**
     * 查询收货地址详情
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<Shipping> selectShipping(Integer userId,Integer shippingId){
        Shipping shipping = shippingMapper.selectByUsserIdAndShippingId(userId,shippingId);
        if(shipping == null){
            return ServerResponse.createByError("查询无返回结果");
        }
        return ServerResponse.createBySuccess("查询成功",shipping);
    }

    /**
     * 返回查询出的收货地址列表以及分页数据
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> selectAllShipping(Integer userId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectAllShipping(userId);
        PageInfo resultPageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(resultPageInfo);
    }
}
