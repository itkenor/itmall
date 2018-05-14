package com.itkenor.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.dao.CartMapper;
import com.itkenor.dao.ProductMapper;
import com.itkenor.pojo.Cart;
import com.itkenor.pojo.Product;
import com.itkenor.service.ICartService;
import com.itkenor.util.BigDecimalUtil;
import com.itkenor.util.PropertiesUtil;
import com.itkenor.vo.CartProductVo;
import com.itkenor.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @PACKAGE_NAME:com.itkenor.service.impl
 * @Auther: itkenor
 * @Date: 2018/4/30 23:29
 * @Description:
 */
@Service("iCartService")

public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * add
     * @param userId
     * @param productId
     * @param checkCount
     * @return
     */
    @Override
    public ServerResponse<CartVo> addProductToCart(Integer userId,Integer productId,Integer checkCount){
        if(checkCount == null || productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId,productId);
        //若购物车中不存在该商品，则添加，否则数量加一
        if(cart == null){
            Cart cartItem = new Cart();
            cartItem.setQuantity(checkCount);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        }else {
            checkCount = cart.getQuantity() + checkCount;
            cart.setQuantity(checkCount);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.selectProductToCart(userId);
    }

    /**
     * 更新购物车
     * @param userId
     * @param productId
     * @param checkCount
     * @return
     */
    @Override
    public ServerResponse<CartVo> updateProductToCart(Integer userId,Integer productId,Integer checkCount){
        if(checkCount == null || productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if(cart != null ){
            cart.setQuantity(checkCount);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.selectProductToCart(userId);
    }

    /**
     * 删除购物车的商品
     * @param userId
     * @param productIds
     * @return
     */
    @Override
    public ServerResponse<CartVo> deleteProductToCart(Integer userId,String productIds){
        List<String> productsList = Splitter.on(",").splitToList(productIds);
        System.out.println(productsList.toString());
        if(CollectionUtils.isEmpty(productsList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        };
        cartMapper.delectCartProductCheckedByUserIdAndProductIds(userId,productsList);
        return this.selectProductToCart(userId);
    }

    /**
     * 查询购物车的商品
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<CartVo> selectProductToCart(Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 购物车全选或全不选
     * 单选或取消单选
     * @param checked
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<CartVo> selectOrUnselect(Integer checked,Integer userId,Integer productId){
        cartMapper.selectOrUnselect(checked,userId,productId);
        return this.selectProductToCart(userId);
    }

    /**
     * 查询购物车中的商品数量
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }


    public CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if(CollectionUtils.isNotEmpty(cartList)){
            //start
            //向CartProductVo中填充数据
            for(Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null ){
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStock(product.getStock());
                    int buyLimitCount = 0;
                    if(product.getStock() >= cartItem.getQuantity()){
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        Cart updateCart = new Cart();
                        updateCart.setUserId(userId);
                        updateCart.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(updateCart);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(cartProductVo.getQuantity(),cartProductVo.getProductPrice().doubleValue()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                //判断是否为选中状态
                //是，则计算商品总价
                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        //end
        //start
        //向CartVo中填充数据
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHostName(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    /**
     * 判断购物车是否全选状态
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null ){
            return false;
        }
        //若查查结果为0，则表示为全选状态
        //否则为非全选状态
        return cartMapper.setlectCartProductCheckedByUserId(userId) == 0;
    }
}
