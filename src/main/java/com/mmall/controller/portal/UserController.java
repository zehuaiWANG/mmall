package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;
    /*
    * 用户登录
    * */
    @RequestMapping(value ="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /*
    * 登出功能
    * */
    @RequestMapping(value ="logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /*
    * 注册功能
    * */
    @RequestMapping(value ="reister.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }


    /*
    * 校验功能
    * */
    @RequestMapping(value ="checkValid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String>checkValid(String str,String type){
    return iUserService.checkValid(str,type);
    }

    /*
    * 获取用户信息功能
    * */
    @RequestMapping(value ="get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return  ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    /*
    * 忘记密码问题功能
    * */
    @RequestMapping(value ="forget_get_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    /*
    * 检测密码问题功能
    * */
    @RequestMapping(value ="forget_check_answer.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /*
    * 重置密码功能
    * */
    @RequestMapping(value ="forget_reset_password.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetRestPassword(username,passwordNew,forgetToken);
    }

    /*
    * 登录状态的重置密码
    * */
    @RequestMapping(value ="reset_password.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordMew){
    User user = (User)session.getAttribute(Const.CURRENT_USER);
    if(user == null){
        return ServerResponse.createByErrorMessage("用户未登录");
    }
    return iUserService.resetPassword(passwordOld,passwordMew,user);
    }

    /*
    * 更新用户信息
    * */
    @RequestMapping(value ="update_information.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response =iUserService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }


}
