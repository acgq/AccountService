package com.github.accounting.dao;

import com.github.accounting.model.persistence.UserInfoInPersistence;

public interface UserInfoDao {
    UserInfoInPersistence getUserInfoByUserId(Long userId);

    UserInfoInPersistence getUserInfoByUsername(String username);

    void createUserInfo(UserInfoInPersistence userInfo);
}
