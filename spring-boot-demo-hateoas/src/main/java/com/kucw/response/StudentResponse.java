package com.kucw.response;

import org.springframework.hateoas.RepresentationModel;

public class StudentResponse extends RepresentationModel<StudentResponse> {
    private Integer studentId;
    private String name;
    private String gender;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
