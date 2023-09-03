package com.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Acu;
import com.entity.User;
import com.mapper.UserMapper;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //登录
    @Override
    public int Login(String id, String password) {
        User user = userMapper.login(id, password);
        if(user != null){
            return user.getKind();
        }
        else return -1;
    }

    //分页查询用户
    @Override
    public List<User> selectUser(int page,int pageSize) {

        Page<User> user = new Page<>(page,pageSize);
        userMapper.selectPage(user,null);

        System.out.println("当前页码值"+user.getCurrent());
        System.out.println("每页显示数"+user.getSize());
        System.out.println("一共多少页"+user.getPages());
        System.out.println("一共多少条数据"+user.getTotal());
        System.out.println("数据"+user.getRecords());

        return user.getRecords();
    }

    //添加用户
    @Override
    public void addUser(String id, String password, int kind) {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setKind(kind);
        userMapper.insert(user);
    }

    //删除用户
    @Override
    public void delete(String id) {
        userMapper.deleteById(id);
    }


}
