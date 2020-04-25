package com.github.accountService.controller;

import com.github.accountService.converter.c2s.UserInfoConverterC2S;
import com.github.accountService.manager.UserInfoManager;
import com.github.accountService.model.commom.UserInfo;
import com.github.accountService.model.service.UserInfoInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {

    private final UserInfoManager userInfoManager;
    private final UserInfoConverterC2S converter;

    @Autowired
    public UserController(UserInfoManager userInfoManager, UserInfoConverterC2S converter) {
        this.userInfoManager = userInfoManager;
        this.converter = converter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoInService> getUserInfoByUserId(@PathVariable("id") long userId) {
        log.debug("Get user info by user id: " + userId);
        UserInfo userInfo = userInfoManager.getUserInfoByUserId(userId);
        return ResponseEntity.ok(converter.convert(userInfo));
    }
}
