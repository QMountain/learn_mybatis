package com.qm.learnmybatis.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {
    private Long id;
    private String name;
    private Integer age;
    private String grade;
    private String email;
}