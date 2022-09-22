package com.qm.learnmybatis.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    public Long id;

    public String name;

    public Integer age;
}
