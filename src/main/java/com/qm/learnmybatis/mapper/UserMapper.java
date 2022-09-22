package com.qm.learnmybatis.mapper;

import com.qm.learnmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectOneById(Long id);
}
