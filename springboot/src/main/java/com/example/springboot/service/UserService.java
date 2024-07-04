/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/17 15:17
 */
package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    UserMapper userMapper;
//    public void insertUser(User user) {
//        userMapper.insertUser(user);
//    }
//
//    public void updateUser(User user) {
//        userMapper.updateUser(user);
//    }
//
//    public void deleteUser(Integer id) {
//        userMapper.deleteUser(id);
//    }
//
//    //批量删除，循环调用单个删除
//    public void batchDeleteUser(List<Integer> ids) {
//        for(Integer id :ids){
//            userMapper.deleteUser(id);
//        }
//    }
//
//    public List<User> selectUser() {
//        return userMapper.selectUser();
//    }
//
    public User selectByIdUser(Integer id) {
        return userMapper.selectByIdUser(id);
    }
//
//    public User selectMoreUser(Integer id, String name) {
//        return userMapper.selectMoreUser(id, name);
//    }
//
//    public List<User> selectMo(String name) {
//        return userMapper.selectMo(name);
//    }
//
//    public List<User> selectByPage(Integer pageNum, Integer pageSize, String name) {
//        Integer skipNum = (pageNum - 1) * pageSize;
//        return userMapper.selectByPage(skipNum, pageSize, name);
//    }
    public User selectByname(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(); //eq => where name = #{name}
        queryWrapper.eq("name", name);
        return getOne(queryWrapper); //select * where name = #{name}
    }

    public User login(User user) {
        User userDb = selectByname(user.getName());
        if (userDb == null) {
            //自定义异常
            throw new ServiceException("账号不存在");
        }
        if (!user.getPassword().equals(userDb.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        //生成token
        String token = TokenUtils.createToken(userDb.getId().toString(), userDb.getPassword());
        userDb.setToken(token);
        return userDb;
    }

    public User register(User user) {
        User userDb = selectByname(user.getName());
        if (userDb != null) {
            //自定义异常
            throw new ServiceException("账号已存在");
        }
        userMapper.insert(user);
        return user;
    }

    public void resetPassword(User user) {
        User userDb = selectByname(user.getName());
        if (userDb == null) {
            throw new ServiceException("用户不存在");
        }
        if (!user.getPhone().equals(userDb.getPhone())){
            throw new ServiceException("验证错误");
        }
        userDb.setPassword("123456");
        updateById(userDb);
    }

    public boolean save (User entity) {
        if (StrUtil.isBlank(entity.getPassword())) {
            entity.setPassword("123");
        }
        if (StrUtil.isBlank(entity.getRole())) {
            entity.setRole("用户");
        }
        return super.save(entity);
    }

    public User getByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return userMapper.selectOne(queryWrapper);
    }

    public User setBalance(String name, int balance) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        User user = userMapper.selectOne(queryWrapper);

        if (user != null) {
            user.setBalance(balance); // 设置余额
        }
        return user;
    }

    public void updateUser(User user) {
        // 使用 MyBatis Plus 的 update 方法更新用户信息
        this.updateById(user);
    }


//    public boolean removeById(User entity) {
//        User currentUser = TokenUtils.getCurrentUser();
//        if (entity.getId().equals(currentUser.getId())) {
//            throw new ServiceException("不能删除当前用户");
//        }
//        return super.removeById((Serializable) entity);
//    }

//    public boolean removeByIds(Collection<?> list) {
//        User currentUser = TokenUtils.getCurrentUser();
//        if (currentUser.getId() != null && list.contains(currentUser.getId())) {
//            throw new ServiceException("不能删除当前用户");
//        }
//        return super.removeByIds(list);
//    }
}
