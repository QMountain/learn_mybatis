package com.qm.learnmybatis.service.impl;

import com.qm.learnmybatis.entity.User;
import com.qm.learnmybatis.mapper.UserMapper;
import com.qm.learnmybatis.service.IUserService;
import com.qm.learnmybatis.util.CacheManager;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    
    @Autowired
    private CacheManager cacheManager;

    @Override
    public User selectOne(Long id) throws Exception {
        // 先从缓存中获取
        String cacheKey = "user_" + id;
        User cachedUser = cacheManager.get(cacheKey);
        if (cachedUser != null) {
            System.out.println("从缓存中获取用户: " + cachedUser);
            return cachedUser;
        }
        
        // 缓存中没有，从数据库获取
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.selectOneById(id);
            System.out.println("从数据库获取用户: " + user);
            
            // 将结果存入缓存，有效期5分钟
            if (user != null) {
                cacheManager.put(cacheKey, user, 5, TimeUnit.MINUTES);
            }

            List<Callable<User>> callableList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                callableList.add(() -> mapper.selectOneById(id));
            }
            ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
            List<Future<User>> futures = executor.invokeAll(callableList);
            for (Future<User> future : futures) {
                System.out.println("并发查询结果: " + future.get().toString());
            }
            return user;
        }
    }
}
