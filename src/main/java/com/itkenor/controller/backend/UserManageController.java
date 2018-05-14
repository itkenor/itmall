package com.itkenor.controller.backend;


import com.itkenor.common.Const;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.User;
import com.itkenor.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @PACKAGE_NAME:com.itkenor.controller.backend
 * @Auther: itkenor
 * @Date: 2018/4/29 10:31
 * @Description:管理员登录模块
 */

@Controller
@RequestMapping("/manage/user")
public class UserManageController {
    @Autowired
    private IUserService iUserService;

    /**
     * 管理员登录接口
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login.do")
    @ResponseBody
    public ServerResponse<User> login(HttpSession session,String username,String password){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole()==Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServerResponse.createByError("");
            }
        }
        return response;
    }
}
