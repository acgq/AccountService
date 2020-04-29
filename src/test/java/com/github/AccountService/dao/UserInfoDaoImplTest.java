package com.github.AccountService.dao;

import com.github.accountService.dao.UserInfoDaoImpl;
import com.github.accountService.dao.mapper.UserInfoMapper;
import com.github.accountService.model.persistence.UserInfoInPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserInfoDaoImplTest {
    @Mock
    UserInfoMapper userInfoMapper;

    @InjectMocks
    UserInfoDaoImpl userInfoDao;

    @Test
    public void testGetUserInfoByUserId() {
        //arrange

        //act
        UserInfoInPersistence userInfoByUserId = userInfoDao.getUserInfoByUserId(1L);

        //assert
        verify(userInfoMapper, only()).getUserInfoByUserId(1L);
    }

}