package com.github.accounting.controller;

import com.github.accounting.converter.c2s.UserInfoConverterC2S;
import com.github.accounting.exception.InvalidParameterException;
import com.github.accounting.manager.UserInfoManager;
import com.github.accounting.model.service.UserInfoInService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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

    /**
     * Get user by user id.
     *
     * @param userId user id.
     * @return User information.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoInService> getUserInfoByUserId(@PathVariable("id") long userId) {
        log.debug("Get user info by user id: " + userId);
        if (userId <= 0) {
            throw new InvalidParameterException(String.format("invalid user id %s", userId));
        }
        return Optional.ofNullable(userInfoManager.getUserInfoByUserId(userId))
                .map(converter::convert)
                .map(ResponseEntity::ok)
                .get();
    }
}
