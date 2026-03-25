package com.qm.learnmybatis.service.impl;

import com.qm.learnmybatis.entity.Student;
import com.qm.learnmybatis.mapper.StudentMapper;
import com.qm.learnmybatis.service.IStudentService;
import com.qm.learnmybatis.util.CacheManager;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements IStudentService {
    
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    
    @Autowired
    private CacheManager cacheManager;

    @Override
    public Student getStudentById(Long id) {
        // 先从缓存中获取
        String cacheKey = "student_" + id;
        Student cachedStudent = cacheManager.get(cacheKey);
        if (cachedStudent != null) {
            System.out.println("从缓存中获取学生: " + cachedStudent);
            return cachedStudent;
        }
        
        // 缓存中没有，从数据库获取
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            Student student = mapper.selectById(id);
            
            // 将结果存入缓存，有效期5分钟
            if (student != null) {
                cacheManager.put(cacheKey, student, 5, TimeUnit.MINUTES);
            }
            
            return student;
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public boolean addStudent(Student student) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            int result = mapper.insert(student);
            sqlSession.commit();
            
            // 添加成功后，清除相关缓存
            if (result > 0) {
                cacheManager.clear();
            }
            
            return result > 0;
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            int result = mapper.update(student);
            sqlSession.commit();
            
            // 更新成功后，清除相关缓存
            if (result > 0) {
                cacheManager.remove("student_" + student.getId());
            }
            
            return result > 0;
        }
    }

    @Override
    public boolean deleteStudent(Long id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            int result = mapper.deleteById(id);
            sqlSession.commit();
            
            // 删除成功后，清除相关缓存
            if (result > 0) {
                cacheManager.remove("student_" + id);
            }
            
            return result > 0;
        }
    }
}