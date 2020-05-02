package com.github.accounting.manager;

import com.github.accounting.converter.p2c.UserInfoConverterP2C;
import com.github.accounting.dao.UserInfoDao;
import com.github.accounting.exception.ResourceNotFoundException;
import com.github.accounting.model.commom.UserInfo;

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
        return Optional
                .ofNullable(userInfoDao.getUserInfoByUserId(userId))
                .map(converter::convert)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("User %s not found", userId)
                        ));
    }
}
