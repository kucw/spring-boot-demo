package com.kucw.service;

import com.kucw.dao.UserDao;
import com.kucw.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    public Integer insertUser(User user) {
        return userDao.insertUser(user);
    }

    public void print() {
        System.out.println("This is user service!");
    }
}
