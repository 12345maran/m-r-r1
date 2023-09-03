package com.service;

import com.entity.User;

import java.util.List;

public interface UserService {
    //登录
    public int Login(String id,String password);

    //分页查询用户
    public List<User> selectUser(int page,int pageSize);

    //添加用户
    public void addUser(String id,String password,int kind);

    //删除用户
    public void delete(String id);
}

