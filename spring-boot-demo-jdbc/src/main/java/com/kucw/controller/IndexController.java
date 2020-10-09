package com.kucw.controller;

import com.kucw.dao.StudentDao;
import com.kucw.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private StudentDao studentDao;

    @RequestMapping("insert")
    public String insert(){
        Student student = new Student();
        student.setName("John");
        student.setAge(20);

        studentDao.insert(student);
        return "success";
    }

    @RequestMapping("get")
    public Student get(){
        return studentDao.getById(1);
    }
}
