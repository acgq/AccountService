package com.github.accountService.manager;

import com.github.accountService.model.commom.UserInfo;

public interface UserInfoManager {
    UserInfo getUserInfoByUserId(Long userId);
}
