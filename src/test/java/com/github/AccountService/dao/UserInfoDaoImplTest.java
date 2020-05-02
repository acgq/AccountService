package com.github.AccountService.dao;

import com.github.accounting.dao.UserInfoDaoImpl;
import com.github.accounting.dao.mapper.UserInfoMapper;
import com.github.accounting.model.persistence.UserInfoInPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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