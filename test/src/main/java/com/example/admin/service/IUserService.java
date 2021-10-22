package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王铁锤
 * @since 2021-08-15
 */
public interface IUserService extends IService<User> {
    User login(String userName, String password);

    /**
     * 根据用户名查询用户记录
     * @param userName
     * @return
     */
    User findUserByUserName(String userName);

    void updateUser(User user);

    void updatePassword(String userName, String oldPassword, String newPassword, String confirmPassword);
}
