package com.github.accounting.dao;

import com.github.accounting.model.persistence.UserInfoInPersistence;

public interface UserInfoDao {
    UserInfoInPersistence getUserInfoByUserId(Long userId);
}
