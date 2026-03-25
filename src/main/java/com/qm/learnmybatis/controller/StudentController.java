package com.qm.learnmybatis.controller;

import com.qm.learnmybatis.dto.StudentDTO;
import com.qm.learnmybatis.entity.Student;
import com.qm.learnmybatis.service.IStudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private IStudentService studentService;
    
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
    
    @GetMapping("/list")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    
    @PostMapping("/add")
    public String addStudent(@RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        boolean result = studentService.addStudent(student);
        return result ? "添加成功，学生ID: " + student.getId() : "添加失败";
    }
    
    @PutMapping("/update")
    public String updateStudent(@RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        boolean result = studentService.updateStudent(student);
        return result ? "更新成功" : "更新失败";
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        boolean result = studentService.deleteStudent(id);
        return result ? "删除成功" : "删除失败";
    }
}