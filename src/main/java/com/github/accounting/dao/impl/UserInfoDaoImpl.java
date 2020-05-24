package com.github.accounting.dao.impl;

import com.github.accounting.dao.UserInfoDao;
import com.github.accounting.dao.mapper.UserInfoMapper;
import com.github.accounting.model.persistence.UserInfoInPersistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoDaoImpl implements UserInfoDao {

    private final UserInfoMapper mapper;

    @Autowired
    public UserInfoDaoImpl(UserInfoMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserInfoInPersistence getUserInfoByUserId(Long userId) {
        return mapper.getUserInfoByUserId(userId);
    }

    @Override
    public UserInfoInPersistence getUserInfoByUsername(String username) {
        return mapper.getUserInfoByUsername(username);
    }

    @Override
    public void createUserInfo(UserInfoInPersistence userInfo) {
        mapper.createUserInfo(userInfo);
    }
}
