package com.kucw.controller;

import com.kucw.dao.UserDao;
import com.kucw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/user/get")
    public User get(@RequestParam Integer id) {
        return userDao.getUserById(id);
    }

    @PostMapping("/user/insert/{name}")
    public Integer insert(@PathVariable String name) {
        User user = new User();
        user.setName(name);
        user.setUpdateTime(new Date());
        return userDao.insertUser(user);
    }
}

