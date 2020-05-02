package com.github.accounting.manager;

import com.github.accounting.model.commom.UserInfo;

public interface UserInfoManager {
    UserInfo getUserInfoByUserId(Long userId);
}
