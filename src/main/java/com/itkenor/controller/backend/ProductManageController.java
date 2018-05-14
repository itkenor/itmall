package com.itkenor.controller.backend;

import com.google.common.collect.Maps;
import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.Product;
import com.itkenor.pojo.User;
import com.itkenor.service.IFileService;
import com.itkenor.service.IProductService;
import com.itkenor.service.IUserService;
import com.itkenor.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @PACKAGE_NAME:com.itkenor.controller.backend
 * @Auther: itkenor
 * @Date: 2018/4/29 21:09
 * @Description:
 */

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    /**
     * 更新或增加商品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("productSave.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            return iProductService.saveORUpdateProduct(product);
        }
        return ServerResponse.createByError("非法用户");
    }

    /**
     * 设置商品的状态
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("setSaleStatus.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId,Integer status ){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
           return iProductService.setSaleStatus(productId,status);
        }
        return ServerResponse.createByError("非法用户");
    }

    /**
     * 获取商品详情
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("getProductDetail.do")
    @ResponseBody
    public ServerResponse getProductDetail(HttpSession session, Integer productId){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            return iProductService.manageProductDetail(productId);
        }
        return ServerResponse.createByError("非法用户");
    }

    /**
     * 获取商品详情
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("getProductList.do")
    @ResponseBody
    public ServerResponse getProductList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            return iProductService.getProductList(pageNum,pageSize);
        }

        return ServerResponse.createByError("非法用户");
    }

    /**
     * 根据条件查询返回分页信息和productListVoid对象
     * @param session
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("searchProduct.do")
    @ResponseBody
    public ServerResponse searchProduct(HttpSession session,String productName ,Integer productId, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            return iProductService.getProductList(pageNum,pageSize);
        }

        return ServerResponse.createByError("非法用户");
    }

    /**
     * 上传图片到图片服务器，并将图片的名称及地址返回
     * @param session
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session,@RequestParam(value = "uploadFile",required = false) MultipartFile multipartFile , HttpServletRequest request){
        String path = null;
        String fileName = null;
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再操作");
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            //获取图片的上传路径
            path = request.getSession().getServletContext().getRealPath("upload");
            //获取上传后的图片名
            fileName = iFileService.uploadFile(path,multipartFile);
            //获取图片的url地址
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + fileName;
            //将图片名及地址保存到map中
            Map resultMap = Maps.newHashMap();
            resultMap.put("uri",fileName);
            resultMap.put("url",url);
            //返回map
            return ServerResponse.createBySuccess(resultMap);
        }
        return ServerResponse.createByError("非法用户");
    }

    /**
     * 上传图片到图片服务器，并将图片的名称及地址返回
     * @param session
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(value = "richTextImgUpload.do",method = RequestMethod.POST)
    @ResponseBody
    public Map richTextImgUpload(HttpSession session, @RequestParam(value = "uploadFile",required = false) MultipartFile multipartFile , HttpServletRequest request, HttpServletResponse response){
        String path = null;
        String fileName = null;
        Map resultMap = Maps.newHashMap();
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            resultMap.put("success",false);
            resultMap.put("msg","管理员未登录，请登录后再操作");
            return resultMap;
        }
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            //获取图片的上传路径
            path = request.getSession().getServletContext().getRealPath("upload");
            //获取上传后的图片名
            fileName = iFileService.uploadFile(path,multipartFile);
            //获取图片的url地址
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + fileName;
            //将图片名及地址保存到map中
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","非法用户");
            return resultMap;
        }
    }
}
