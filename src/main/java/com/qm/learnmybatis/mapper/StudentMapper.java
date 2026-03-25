package com.qm.learnmybatis.mapper;

import com.qm.learnmybatis.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    Student selectById(Long id);
    List<Student> selectAll();
    int insert(Student student);
    int update(Student student);
    int deleteById(Long id);
}