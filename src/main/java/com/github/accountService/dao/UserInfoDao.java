package com.github.accountService.dao;

import com.github.accountService.model.persistence.UserInfoInPersistence;

public interface UserInfoDao {
    UserInfoInPersistence getUserInfoByUserId(Long userId);
}
