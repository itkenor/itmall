package com.itkenor.dao;

import com.itkenor.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectOrderByUserIdAndOrderNo(@Param("userId")Integer userId,@Param("orderNo") Long orderNo);

    Order selectOrderByOrderNo(Long orderNo);

    List<Order> selectOrderByUserId(Integer userId);

    List<Order> selectAllOrder();

}