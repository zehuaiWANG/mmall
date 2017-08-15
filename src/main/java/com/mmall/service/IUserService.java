package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

import java.util.List;

/**
  * @Author: wzh
  * @Description:
  * @Date: Created in 10:29 2017/7/31
  */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str, String type);
    ServerResponse selectQuestion(String username);
    ServerResponse<String> checkAnswer(String username,String question,String answer);
    ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken);
    ServerResponse<String>resetPassword(String passwordOld,String passwordNew,User user);
    ServerResponse<User>updateInformation(User user);
    ServerResponse<User> getInformation(Integer userId);
    ServerResponse checkAdminRole(User user);
}
