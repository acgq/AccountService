package com.github.accountService.manager;

import com.github.accountService.converter.p2c.UserInfoConverterP2C;
import com.github.accountService.dao.UserInfoDao;
import com.github.accountService.exception.InvalidParameterException;
import com.github.accountService.exception.ResourceNotFoundException;
import com.github.accountService.model.commom.UserInfo;
import com.github.accountService.model.persistence.UserInfoInPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoManagerImpl implements UserInfoManager {

    private final UserInfoDao userInfoDao;
    private final UserInfoConverterP2C converter;

    @Autowired
    public UserInfoManagerImpl(UserInfoDao userInfoDao, UserInfoConverterP2C converter) {
        this.userInfoDao = userInfoDao;
        this.converter = converter;
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        UserInfoInPersistence userInfo = Optional
                .ofNullable(userInfoDao.getUserInfoByUserId(userId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", userId)));
        return converter.convert(userInfo);
    }
}
