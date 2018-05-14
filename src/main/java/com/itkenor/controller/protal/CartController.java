package com.itkenor.controller.protal;

import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.User;
import com.itkenor.service.ICartService;
import com.itkenor.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @PACKAGE_NAME:com.itkenor.controller.protal
 * @Auther: itkenor
 * @Date: 2018/4/30 23:19
 * @Description: 用户的购物车操作
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService iCartService;

    /**
     * 功能需求
     * 1.添加购物车
     * 2.更新购物车
     * 3.删除购物车的商品
     * 4.查询购物车的商品
     * 5.全选
     * 6.全不选
     * 7.单选
     * 8.取消单选
     * 9.查询用户当前购物车商品的数量
     */



    /**
     * 添加购物车
     * @param session
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping("addProductToCart.do")
    @ResponseBody
    public ServerResponse<CartVo> addProductToCart(HttpSession session, Integer count, Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.addProductToCart(user.getId(),productId,count);
    }

    /**
     * 更新购物车
     * @param session
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping("updateProductToCart.do")
    @ResponseBody
    public ServerResponse<CartVo> updateProductToCart(HttpSession session, Integer count, Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.updateProductToCart(user.getId(),productId,count);
    }

    /**
     * 删除购物车的商品
     * @param session
     * @param productIds
     * @return
     */
    @RequestMapping("deleteProductToCart.do")
    @ResponseBody
    public ServerResponse<CartVo> deleteProductToCart(HttpSession session,String productIds){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProductToCart(user.getId(),productIds);
    }

    /**
     * 查询购物车的商品
     * @param session
     * @return
     */
    @RequestMapping("selectProductToCart.do")
    @ResponseBody
    public ServerResponse<CartVo> selectProductToCart(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectProductToCart(user.getId());
    }

    /**
     * 全选
     * @param session
     * @return
     */
    @RequestMapping("checkedAllProduct.do")
    @ResponseBody
    public ServerResponse<CartVo> checkedAllProduct(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnselect(Const.Cart.CHECKED,user.getId(),null);
    }

    /**
     * 全不选
     * @param session
     * @return
     */
    @RequestMapping("unCheckedAllProduct.do")
    @ResponseBody
    public ServerResponse<CartVo> unCheckedAllProduct(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnselect(Const.Cart.UN_CHECKED,user.getId(),null);
    }

    /**
     * 单选
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("CheckedProduct.do")
    @ResponseBody
    public ServerResponse<CartVo> CheckedProduct(HttpSession session,Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnselect(Const.Cart.UN_CHECKED,user.getId(),productId);
    }

    /**
     * 取消单选
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("unCheckedProduct.do")
    @ResponseBody
    public ServerResponse<CartVo> unCheckedProduct(HttpSession session,Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnselect(Const.Cart.UN_CHECKED,user.getId(),productId);
    }

    /**
     * 查询购物车中的商品数量
     * @param session
     * @return
     */
    @RequestMapping("getCartProductCount.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createBySuccessMessage("0");
        }
        return iCartService.getCartProductCount(user.getId());
    }

}
