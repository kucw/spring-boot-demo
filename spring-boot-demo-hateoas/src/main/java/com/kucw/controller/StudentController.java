package com.kucw.controller;

import com.kucw.response.StudentResponse;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class StudentController {

    @RequestMapping("/student/{studentId}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Integer studentId) {

        // 這部分可以替換為從DB撈data，我這裡是直接寫死了，模擬已經從DB取得到data
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStudentId(studentId);
        studentResponse.setName("John");
        studentResponse.setGender("MALE");

        // 添加 HATEOAS 的 self link
        if (!studentResponse.getLink(IanaLinkRelations.SELF).isPresent()) {
            studentResponse.add(linkTo(methodOn(StudentController.class).getStudent(studentId)).withSelfRel());
        }

        // 添加 HATEOAS 的 course link
        studentResponse.add(linkTo(methodOn(CourseController.class).getCourseByStudentId(studentId)).withRel("course"));

        // 返回結果
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }
}
