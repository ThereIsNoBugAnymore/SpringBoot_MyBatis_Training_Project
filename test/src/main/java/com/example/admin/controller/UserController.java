package com.example.admin.controller;


import com.example.admin.exceptions.ParamsException;
import com.example.admin.model.RespBean;
import com.example.admin.pojo.User;
import com.example.admin.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王铁锤
 * @since 2021-08-15
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public RespBean login(String username, String password, HttpSession session) {
        try {
            User user = userService.login(username, password);
            session.setAttribute("user", user);
            return RespBean.success("用户登录成功！");
        } catch (ParamsException e) {
            e.printStackTrace();
            return RespBean.error(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("用户登录失败！");
        }
    }

    /**
     * 用户信息更新
     * @param user
     * @return
     */
    @RequestMapping("updateUserInfo")
    @ResponseBody
    public RespBean updateUserInfo(User user) {
        try {
            userService.updateUser(user);
            return RespBean.success("用户信息更新成功！");
        } catch (ParamsException e) {
            e.printStackTrace();
            return RespBean.error(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("用户信息更新失败");
        }
    }

    /**
     * 用户密码更新
     * @param session
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @RequestMapping("updateUserPassword")
    @ResponseBody
    public RespBean updateUserPassword(HttpSession session, String oldPassword, String newPassword, String confirmPassword) {
        try {
            User user = (User) session.getAttribute("user");
            userService.updatePassword(user.getUserName(), oldPassword, newPassword, confirmPassword);
            return RespBean.success("用户密码更新成功！");
        } catch (ParamsException e) {
            e.printStackTrace();
            return RespBean.error(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("用户密码更新失败");
        }
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "rediect:index";
    }
}
