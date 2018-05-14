package com.itkenor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.dao.CategoryMapper;
import com.itkenor.dao.ProductMapper;
import com.itkenor.pojo.Category;
import com.itkenor.pojo.Product;
import com.itkenor.service.ICategoryService;
import com.itkenor.service.IProductService;
import com.itkenor.util.DateTimeUtil;
import com.itkenor.util.PropertiesUtil;
import com.itkenor.vo.ProductDetailVo;
import com.itkenor.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME:com.itkenor.service.impl
 * @Auther: itkenor
 * @Date: 2018/4/29 21:22
 * @Description:
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 更新或增加商品
     * @param product
     * @return
     */
    @Override
    public ServerResponse saveORUpdateProduct(Product product){
        if(product!=null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImages = product.getSubImages().split(",");
                if(subImages.length>0){
                    product.setMainImage(subImages[0]);
                }
            }
            if(product.getId()!=null){
                int resultCount = productMapper.updateByPrimaryKeySelective(product);
                if(resultCount==0){
                    return ServerResponse.createByError("更新商品失败");
                }
                return ServerResponse.createBySuccessMessage("更新商品成功");
            }else {
                int resultCount = productMapper.insert(product);
                if(resultCount==0){
                    return ServerResponse.createByError("增加商品失败");
                }
                return ServerResponse.createBySuccessMessage("增加商品成功");
            }
        }
        return ServerResponse.createByError("新增或更新产品参数不正确");
    }

    /**
     * 更新商品状态
     * @param productId
     * @param status
     * @return
     */
    @Override
    public ServerResponse setSaleStatus(Integer productId,Integer status){
        if(productId==null || status==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultCount = productMapper.updateByPrimaryKeySelective(product);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("更新商品销售状态成功");
        }
        return ServerResponse.createByError("更新商品销售状态失败");
    }


    /**
     * 获取商品信息
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByError("商品已经删除或下架");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 一个包装了product的ProductDetailVo
     * @param product
     * @return
     */
    public ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setName(product.getName());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setSubtitle(product.getSubtitle());

        //imageHostName
        productDetailVo.setImageHostName(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        //parentCategoryId
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category==null){
            productDetailVo.setParentCategoryId(0);
        }
        productDetailVo.setParentCategoryId(category.getParentId());
        //createTime
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        //updateTime
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return productDetailVo;
    }

    /**
     * 返回分页信息和productListVoid对象
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(Integer pageNum,Integer pageSize){
        //PageHelper的开始
        //业务逻辑ass
        //PageHelper的收尾
        PageHelper.startPage(pageNum,pageSize);
        //声明一个用于存储包装了Product的ProductListVo集合
        List<ProductListVo> productListVoList = Lists.newArrayList();
        //将所有查询结果的Product集合存储到集中
        List<Product> productList = productMapper.selectList();
        if(productList==null || productList.size()==0){
            return ServerResponse.createByError("商品已经删除或下架");
        }
        for(Product product : productList){
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 返回一个Product数据的包装类
     * @param product
     * @return
     */
    public ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setId(product.getId());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setImageHostName(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        return productListVo;
    }

    /**
     * 根据条件查询返回分页信息和productListVoid对象
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> searchProductList(String productName ,Integer productId,Integer pageNum,Integer pageSize){
        //PageHelper的开始
        //业务逻辑ass
        //PageHelper的收尾
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        //声明一个用于存储包装了Product的ProductListVo集合
        List<ProductListVo> productListVoList = Lists.newArrayList();
        //将所有查询结果的Product集合存储到集中
        List<Product> productList = productMapper.searchList(productName,productId);
        if(productList==null || productList.size()==0){
            return ServerResponse.createByError("商品已经删除或下架");
        }
        for(Product product : productList){
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     *
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        if(productId==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByError("商品已经删除或下架");
        }
        if(product.getStatus()!= Const.ProductStatus.ON_SALE.getCode()){
            return ServerResponse.createByError("商品已经删除或下架");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 根据关键字或categoryID查询商品
     * @param keyWord
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductByKeyWordAndCategoryId(String keyWord,Integer categoryId,Integer pageNum,Integer pageSize,String orderBy){
        if(StringUtils.isBlank(keyWord) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARUMENT.getCode(),ResponseCode.ILLEGAL_ARUMENT.getDesc());
        }
        List<Integer> categoryList = Lists.newArrayList();
        if(categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyWord)){
                //开始分页
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if(StringUtils.isNotBlank(keyWord)){
            keyWord = new StringBuilder().append("%").append(keyWord).append("%").toString();
        }
        //开始分页
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + "" + orderByArray[1]);
            }
        }
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        List<Product> productList = productMapper.getListByKeyWordAndCategoryIds(StringUtils.isBlank(keyWord)?null:keyWord,categoryList.size()==0?null:categoryList);
        for(Product product : productList){
            ProductDetailVo productDetailVo = assembleProductDetailVo(product);
            productDetailVoList.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}

