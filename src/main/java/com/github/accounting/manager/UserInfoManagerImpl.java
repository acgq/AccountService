package com.github.accounting.manager;

import com.github.accounting.converter.p2c.UserInfoConverterP2C;
import com.github.accounting.dao.UserInfoDao;
import com.github.accounting.exception.InvalidParameterException;
import com.github.accounting.exception.ResourceNotFoundException;
import com.github.accounting.model.commom.UserInfo;
import com.github.accounting.model.persistence.UserInfoInPersistence;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserInfoManagerImpl implements UserInfoManager {

    public static final int HASH_ITERATIONS = 1024;
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

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return Optional
                .ofNullable(userInfoDao.getUserInfoByUsername(username))
                .map(converter::convert)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("User name %s not found", username)
                        ));

    }

    @Override
    public UserInfo registerUser(String username, String password) {
        UserInfoInPersistence userInfoByUsername = userInfoDao.getUserInfoByUsername(username);
        if (userInfoByUsername != null) {
            throw new InvalidParameterException(String.format("user %s was registered", username));
        }

        String salt = UUID.randomUUID().toString();
        String encryptedPassword = new Sha256Hash(password, salt, HASH_ITERATIONS).toBase64();
        UserInfoInPersistence userToBeCreated = UserInfoInPersistence.builder()
                .username(username)
                .password(encryptedPassword)
                .salt(salt)
                .createTime(LocalDate.now())
                .build();

        userInfoDao.createUserInfo(userToBeCreated);
        return converter.convert(userToBeCreated);
    }

    @Override
    public void login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }


}
