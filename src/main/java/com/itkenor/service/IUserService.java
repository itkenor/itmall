package com.itkenor.service;

import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.User;

/**
 * @PACKAGE_NAME:com.itkenor.service
 * @Auther: itkenor
 * @Date: 2018/4/28 15:14
 * @Description:
 */
public interface IUserService {
    //protal
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String val,String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);

    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew ,User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer id);

    //backend
    ServerResponse<String> checkAdminRole(User user);
}
