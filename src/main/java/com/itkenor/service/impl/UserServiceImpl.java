package com.itkenor.service.impl;

import com.itkenor.common.Const;
import com.itkenor.common.ResponseCode;
import com.itkenor.common.ServerResponse;
import com.itkenor.common.TokenCache;
import com.itkenor.dao.UserMapper;
import com.itkenor.pojo.User;
import com.itkenor.service.IUserService;
import com.itkenor.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @PACKAGE_NAME:com.itkenor.service.impl
 * @Auther: itkenor
 * @Date: 2018/4/28 15:17
 * @Description:
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @description 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public ServerResponse<User> login(String username, String password) {
        //保存查询用户名称相匹配的个数
        int resultCount = userMapper.checkUsername(username);
        //若用户名不存在，则保存错误信息
        if (resultCount == 0) {
            return ServerResponse.createByError("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        //保存查询用户名及用户密码相匹配的个数
        //todo 密码md5加密
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByError("密码错误");
        }
        //将用户密码置空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    /**
     * @description 注册
     * @param user
     * @return
     */
    @Override
    public ServerResponse<String> register(User user) {

        //校验用户名和email
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        //设置用户权限
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //将用户密码进行加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        //插入数据
        int resultCount = userMapper.insert(user);
        //判断是否成功
        if (resultCount == 0) {
            //不成功，则返回错误数据
            return ServerResponse.createByError("注册用户失败");
        }
        return ServerResponse.createBySuccessMessage("注册用户成功");
    }

    /**
     * @description 校验
     * @param val
     * @param type
     * @return
     */
    @Override
    public ServerResponse<String> checkValid(String val, String type) {
        //判断type是否为空
        if (StringUtils.isNotBlank(type)) {
            //判断type是哪一种类型
            if (Const.USERNAME.equals(type)) {
                //保存查询用户名称相匹配的个数
                int resultCount = userMapper.checkUsername(val);
                //若用户名不存在，则保存错误信息
                if (resultCount > 0) {
                    return ServerResponse.createByError("用户名已经存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                //保存查询用户email称相匹配的个数
                int resultCount = userMapper.checkEmail(val);
                //若用户名不存在，则保存错误信息
                if (resultCount > 0) {
                    return ServerResponse.createByError("email已经存在");
                }
            }
        }else {
            return ServerResponse.createByError("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    /**
     * @description 找回密码问题
     * @param username
     * @return
     */
    @Override
    public ServerResponse<String> selectQuestion(String username){
         ServerResponse response = this.checkValid(username,Const.USERNAME);
         if(response.isSuccess()){
            ServerResponse.createByError("用户名不存在");
         }
         String question = userMapper.selectQuestionByUsername(username);
         if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
         }
        return ServerResponse.createByError("找回密码的问题是空的");
    }

    /**
     * @description 找回密码
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @Override
    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByError("问题的答案错误");
    }

    /**
     * @description 未登录状态下的修改密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @Override
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByError("参数错误，需要传递Token");
        }
        ServerResponse response = this.checkValid(username,Const.USERNAME);
        if(response.isSuccess()){
            ServerResponse.createByError("用户名不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByError("Token无效或者过期");
        }
        if(StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsernamae(username,md5Password);
            if(rowCount>0){
                return ServerResponse.createBySuccessMessage("修改成功");
            }else {
                return ServerResponse.createByError("修改失败，请从新获取修改密码的token");
            }
        }
        return ServerResponse.createByError("修改密码失败");
    }

    /**
     * @description 登录状态下的修改密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew ,User user){
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld) , user.getId());
        if(resultCount==0){
            return ServerResponse.createByError("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount==0){
            return ServerResponse.createByError("密码修改失败");
        }
        return ServerResponse.createBySuccessMessage("密码修改成功");
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    public ServerResponse<User> updateInformation(User user){
        //校验email是否已经存在
        int resultCount = userMapper.checkEmailById(user.getEmail(),user.getId());
        if(resultCount>0){
            return ServerResponse.createByError("email已经存在，请更换email后更新");
        }
        //username不能被修改
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        resultCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(resultCount==0){
            return ServerResponse.createByError("用户信息更新失败");
        }
        return ServerResponse.createBySuccess("用户信息修改成功",updateUser);
    }

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @Override
    public ServerResponse<User> getInformation(Integer id){
        User currentUser = userMapper.selectByPrimaryKey(id);
        if(currentUser==null){
            return ServerResponse.createByError("找不到当前用户");
        }
        currentUser.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(currentUser);
    }


    /**
     * backend
     */
    @Override
    public ServerResponse<String> checkAdminRole(User user){
        if(user!=null && user.getRole()==Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}