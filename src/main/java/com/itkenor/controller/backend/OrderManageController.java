package com.itkenor.controller.backend;

import com.github.pagehelper.PageInfo;
import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.User;
import com.itkenor.service.IOrderService;
import com.itkenor.service.IUserService;
import com.itkenor.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @PACKAGE_NAME:com.itkenor.controller.backend
 * @Auther: itkenor
 * @Date: 2018/5/6 23:19
 * @Description:
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("orderList.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpSession session, @RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize" ,defaultValue = "10") Integer pageSize){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            return iOrderService.manageList(pageNum,pageSize);
        }else {
            return ServerResponse.createByError("非法用户");
        }
    }


    @RequestMapping("orderDetail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpSession session,Long orderNo){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            return iOrderService.manageDetail(orderNo);
        }else {
            return ServerResponse.createByError("非法用户");
        }
    }

    @RequestMapping("orderSearch.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpSession session,Integer pageNum,Integer pageSize,Long orderNo){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            return iOrderService.manageSearch(pageNum,pageSize,orderNo);
        }else {
            return ServerResponse.createByError("非法用户");
        }
    }

    @RequestMapping("orderSendGoods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpSession session,Long orderNo){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            return iOrderService.manageSendGoods(orderNo);
        }else {
            return ServerResponse.createByError("非法用户");
        }
    }
}
