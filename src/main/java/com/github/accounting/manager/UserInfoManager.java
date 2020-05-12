package com.github.accounting.manager;

import com.github.accounting.model.commom.UserInfo;

public interface UserInfoManager {
    UserInfo getUserInfoByUserId(Long userId);

    void login(String username, String password);

    UserInfo getUserInfoByUsername(String username);

    UserInfo registerUser(String username, String password);
}
