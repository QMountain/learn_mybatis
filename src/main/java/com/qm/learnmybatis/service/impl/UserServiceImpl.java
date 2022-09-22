package com.qm.learnmybatis.service.impl;

import com.qm.learnmybatis.entity.User;
import com.qm.learnmybatis.mapper.UserMapper;
import com.qm.learnmybatis.service.IUserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class UserServiceImpl implements IUserService {

    @Override
    public User selectOne(Long id) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectOneById(1L);
        System.out.println(user);

        List<Callable<User>> callableList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            callableList.add(()-> mapper.selectOneById(1L));
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        List<Future<User>> futures = executor.invokeAll(callableList);
        for (Future<User> future : futures) {
            System.out.println(future.get().toString());
        }
        return user;
    }
}
