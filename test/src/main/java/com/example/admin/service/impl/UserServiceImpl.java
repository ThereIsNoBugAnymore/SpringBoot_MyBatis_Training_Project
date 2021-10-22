package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.mapper.UserMapper;
import com.example.admin.pojo.User;
import com.example.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.utils.AssertUtil;
import com.example.admin.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王铁锤
 * @since 2021-08-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User login(String userName, String password) {
        AssertUtil.isTrue(StringUtil.isEmpty(userName), "有用户名不能为空");
        AssertUtil.isTrue(StringUtil.isEmpty(password), "密码不能为空");
        User user = this.findUserByUserName(userName);
        AssertUtil.isTrue(null == user, "该用户记录不存在或已注销！");
        AssertUtil.isTrue(!user.getPassword().equals(password), "密码错误！");
        return user;
    }

    @Override
    public User findUserByUserName(String userName) {
        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("is_del", 0).eq("user_name", userName));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUser(User user) {
        /**
         * 用户名
         *  非空
         *  唯一
         */
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUserName()), "用户名不可为空");
        User temp = this.findUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "用户名已存在");
        AssertUtil.isTrue(!this.updateById(user), "用户信息更新失败");
    }

    /**
     * 用户密码更新
     * @param userName
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePassword(String userName, String oldPassword, String newPassword, String confirmPassword) {
        /**
         * 用户名非空 必须存在
         * 原始密码 新密码 确认密码不为空
         * 原始密码需正确
         * 新密码 与 确认密码必须一致，并且不能与原密码相同
         */
        User temp = null;
        temp = this.findUserByUserName(userName);
        AssertUtil.isTrue(null == temp, "用户不存在或未登录");
        AssertUtil.isTrue(StringUtil.isEmpty(oldPassword), "请输入原始密码");
        AssertUtil.isTrue(StringUtil.isEmpty(newPassword), "请输入新密码");
        AssertUtil.isTrue(StringUtil.isEmpty(confirmPassword), "请输入确认密码");
        AssertUtil.isTrue(!temp.getPassword().equals(oldPassword), "密码错误");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword), "两次密码不一致");
        AssertUtil.isTrue(newPassword.equals(oldPassword), "新旧密码不得相同");
        temp.setPassword(newPassword);
        AssertUtil.isTrue(!this.updateById(temp), "用户密码更新失败");
    }
}
