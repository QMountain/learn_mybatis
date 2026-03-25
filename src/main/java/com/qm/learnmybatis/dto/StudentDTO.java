package com.qm.learnmybatis.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private String grade;
    private String email;
}