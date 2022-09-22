package com.qm.learnmybatis.service;

import com.qm.learnmybatis.entity.User;

public interface IUserService {

    User selectOne(Long id) throws Exception;
}
