package com.github.accountService.converter.c2s;

import com.github.accountService.model.commom.UserInfo;
import com.github.accountService.model.service.UserInfoInService;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConverterC2S extends Converter<UserInfo, UserInfoInService> {
    @Override
    protected UserInfoInService doForward(UserInfo userInfo) {
        return UserInfoInService.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }

    @Override
    protected UserInfo doBackward(UserInfoInService userInfoInService) {
        return UserInfo.builder()
                .id(userInfoInService.getId())
                .username(userInfoInService.getUsername())
                .password(userInfoInService.getPassword())
                .build();
    }
}
