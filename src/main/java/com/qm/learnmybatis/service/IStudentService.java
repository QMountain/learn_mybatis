package com.qm.learnmybatis.service;

import com.qm.learnmybatis.entity.Student;

import java.util.List;

public interface IStudentService {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    boolean addStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(Long id);
}