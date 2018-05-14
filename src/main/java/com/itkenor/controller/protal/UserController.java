package com.itkenor.controller.protal;
/**
 * @PACKAGE_NAME:com.itkenor.controller.protal
 * @Auther: itkenor
 * @Date: 2018/4/28 15:14
 * @Description:用户登录模块
 */
import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.User;
import com.itkenor.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        //判断response中的status是否为ResponseCode.SUCCESS
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("退出成功");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("register.do")
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    /**
     * 校验
     * @param val
     * @param type
     * @return
     */
    @RequestMapping("checkValid.do")
    @ResponseBody
    public ServerResponse<String> checkValid(String val , String type){
        return iUserService.checkValid(val,type);
    }

    /**
     * 获取登录的用户信息
     * @param session
     * @return
     */
    @RequestMapping("getUserInfo.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByError("用户未登录，无法获取用户信息");
    }

    /**
     * 找回密码问题
     * @param username
     * @return
     */
    @RequestMapping("forgetGetQuestion.do")
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    /**
     * 找回密码
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping("forgetCheckAnswer.do")
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 未登录状态下的重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping("forgetResetPassword.do")
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 登录状态下的重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping("resetPassword.do")
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByError("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 更新用户信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping("updateInformation.do")
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpSession session,User user){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByError("用户未登录");
        }
        //id不能被更新
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> updateResponse = iUserService.updateInformation(user);
        if(updateResponse.isSuccess()){
            updateResponse.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,updateResponse.getData());
        }
        return updateResponse;
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping("getInformation.do")
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，status=10");
        }
        return iUserService.getInformation(user.getId());

    }
}
