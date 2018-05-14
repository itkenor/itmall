package com.itkenor.controller.protal;

import com.github.pagehelper.PageInfo;
import com.itkenor.common.ServerResponse;
import com.itkenor.service.IProductService;
import com.itkenor.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @PACKAGE_NAME:com.itkenor.controller.protal
 * @Auther: itkenor
 * @Date: 2018/4/30 17:23
 * @Description:
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    /**
     * 获取商品信息
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    /**
     * 将根据关键字或categoryID查询出的商品进行price排序
     * @param keyWord
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("showProductList.do")
    @ResponseBody
    public ServerResponse<PageInfo> showProductList(@RequestParam(value = "keyWord",required = false) String keyWord,
                                                    @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                    @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                    @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iProductService.getProductByKeyWordAndCategoryId(keyWord,categoryId,pageNum,pageSize,orderBy);
    }
}
