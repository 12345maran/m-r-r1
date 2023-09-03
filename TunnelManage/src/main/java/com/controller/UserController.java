package com.controller;

import com.entity.User;
import com.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin  //解决跨域问题
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //登录
//    @PostMapping("/Login")
//    public int Login(@RequestParam(name = "id", defaultValue = "xxx") String id,
//                     @RequestParam(name = "password", required = false) String password){
//        int kind = userService.Login(id, password);
//        return kind;
//    }
    @PostMapping("/Login")
    public int Login(@RequestParam(name = "id", defaultValue = "xxx") String id,
                     @RequestParam(name = "password", required = false) String password){
        int kind = userService.Login(id, password);
        return kind;
    }


    //分页查询用户 page:当前页码值  pageSize：每页展示条数
    @GetMapping ("/selectUser")
    public List<User> selectUser(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "2") Integer pageSize){
        List<User> users = userService.selectUser(page,pageSize);
        return users;
    }

    //添加用户
    @PostMapping("/addUser")
    public int addUser(@RequestParam("id") String id,
                       @RequestParam("password") String password,
                       @RequestParam("kind") Integer kind){
        userService.addUser(id, password,kind);
        return 1;
    }

    //删除用户
    @DeleteMapping("/deleteUser/{id}")
    public int delete(@PathVariable("id") String id){
        userService.delete(id);
        return 1;
    }

}
