package com.kucw.controller;

import com.kucw.entity.Student;
import com.kucw.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class IndexController {

    @Autowired
    private StudentRepository studentRepository;

    @RequestMapping("insert")
    public String insert(){
        Student student = new Student();
        student.setId(1);
        student.setName("John");
        student.setAge(20);

        studentRepository.save(student);
        return "insert success";
    }

    @RequestMapping("getById")
    public Student getById(@RequestParam Integer id){
        Optional<Student> studentOpt = studentRepository.findById(id); //這裡用到了Java8新增的Optional類型
        return studentOpt.get();
    }

    @RequestMapping("getByAge")
    public Student getByAge(@RequestParam Integer age){
        return studentRepository.findByAge(age);
    }
}
