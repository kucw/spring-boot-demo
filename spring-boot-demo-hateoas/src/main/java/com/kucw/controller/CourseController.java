package com.kucw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    // 取得該學生修的課的detail資訊
    @RequestMapping("/course")
    public ResponseEntity<String> getCourseByStudentId(@RequestParam Integer studentId) {
        // 本來這裡應該也要做 HATEOAS，但是先專注在StudentController上的語法就好，所以這裡就隨意返回值了
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
