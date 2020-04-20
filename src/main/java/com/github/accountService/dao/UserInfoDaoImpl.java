package com.github.accountService.dao;

import com.github.accountService.dao.mapper.UserInfoMapper;
import com.github.accountService.manager.UserInfoManager;
import com.github.accountService.model.persistence.UserInfoInPersistence;
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
}
