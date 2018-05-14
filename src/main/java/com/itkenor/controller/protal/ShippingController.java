package com.itkenor.controller.protal;

import com.github.pagehelper.PageInfo;
import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.Shipping;
import com.itkenor.pojo.User;
import com.itkenor.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @PACKAGE_NAME:com.itkenor.controller.protal
 * @Auther: itkenor
 * @Date: 2018/5/2 21:00
 * @Description: 收货地址
 */
@Controller
@RequestMapping("/shipping")
public class ShippingController {
    @Autowired
    private IShippingService iShippingService;


    /**
     * 添加地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping("insertShipping.do")
    @ResponseBody
    public ServerResponse insertShipping(HttpSession session,Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null ){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.insertShipping(user.getId(),shipping);
    }


    /**
     * 删除收货地址
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping("deleteShipping.do")
    @ResponseBody
    public ServerResponse<String> deleteShipping(HttpSession session,Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null ){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.deleteShipping(user.getId(),shippingId);
    }

    /**
     * 更新收货地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping("updateShipping.do")
    @ResponseBody
    public ServerResponse updateShipping(HttpSession session,Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null ){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.updateShipping(user.getId(),shipping);
    }

    /**
     * 查询收货地址详情
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping("selectShipping.do")
    @ResponseBody
    public ServerResponse<Shipping> selectShipping(HttpSession session, Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null ){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.selectShipping(user.getId(),shippingId);
    }

    /**
     * 将查询的地址结果进行分页
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("selectAllShipping.do")
    @ResponseBody
    public ServerResponse<PageInfo> selectAllShipping(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null ){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.selectAllShipping(user.getId(),pageNum,pageSize);
    }
}
