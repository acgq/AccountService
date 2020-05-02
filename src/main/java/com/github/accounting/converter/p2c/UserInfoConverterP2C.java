package com.github.accounting.converter.p2c;

import com.github.accounting.model.commom.UserInfo;
import com.github.accounting.model.persistence.UserInfoInPersistence;

import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConverterP2C extends Converter<UserInfoInPersistence, UserInfo> {

    @Override
    protected UserInfo doForward(UserInfoInPersistence userInfoInPersistence) {

        return UserInfo.builder()
                .id(userInfoInPersistence.getId())
                .username(userInfoInPersistence.getUsername())
                .password(userInfoInPersistence.getPassword())
                .build();
    }

    @Override
    protected UserInfoInPersistence doBackward(UserInfo userInfo) {
        return UserInfoInPersistence.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
}
