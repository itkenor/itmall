package com.itkenor.controller.backend;

import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.Category;
import com.itkenor.pojo.User;
import com.itkenor.service.ICategoryService;
import com.itkenor.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @PACKAGE_NAME:com.itkenor.controller.backend
 * @Auther: itkenor
 * @Date: 2018/4/29 13:34
 * @Description:
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {
    //注入接口
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IUserService iUserService;

    /**
     * 添加商品信息
     * @param session
     * @param categoryName
     * @param parent_id
     * @return
     */
    @RequestMapping(value = "addCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addCategory(HttpSession session , String categoryName ,
                                              @RequestParam(value = "parent_id",defaultValue = "0") Integer parent_id){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再进行从操作");
        }
        //判断登录用户是否为管理员
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            //添加商品操作的逻辑代码
            return iCategoryService.addCategory(categoryName,parent_id);
        }else {
            return ServerResponse.createByError("非法操作");
        }
    }

    /**
     * 修改商品名称
     * @param session
     * @param categoryName
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "setCategoryName.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setCategoryName(HttpSession session,String categoryName,Integer categoryId){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再进行从操作");
        }
        //判断登录用户是否为管理员
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            //添加商品操作的逻辑代码
            return iCategoryService.updateCategoryName(categoryName,categoryId);
        }else {
            return ServerResponse.createByError("非法操作");
        }
    }

    /**
     * 查找该分类下的子分类
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "getChildernParallelCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Category>> getChildernParallelCategory(HttpSession session,
                                                                      @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再进行从操作");
        }
        //判断登录用户是否为管理员
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            //添加商品操作的逻辑代码
            //查询子节点的category信息，不需要递归,保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else {
            return ServerResponse.createByError("非法操作");
        }
    }

    /**
     * 查询当前节点的id以及子节点的id
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "getCategoryAndDeepChildrenCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
                                                                 @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录后再进行从操作");
        }
        //判断登录用户是否为管理员
        if(iUserService.checkAdminRole(currentUser).isSuccess()){
            //添加商品操作的逻辑代码
            //查询当前节点的id以及递归子节点的id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else {
            return ServerResponse.createByError("非法操作");
        }
    }



}
