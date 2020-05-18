package com.github.AccountService.mannager;

import com.github.accounting.converter.p2c.UserInfoConverterP2C;
import com.github.accounting.dao.UserInfoDaoImpl;
import com.github.accounting.manager.UserInfoManagerImpl;
import com.github.accounting.model.commom.UserInfo;
import com.github.accounting.model.persistence.UserInfoInPersistence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
public class UserInfoManagerTest {
    UserInfoConverterP2C converter = new UserInfoConverterP2C();
    @Mock
    UserInfoDaoImpl userInfoDao;
    //    @InjectMocks
    UserInfoManagerImpl userInfoManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userInfoManager = new UserInfoManagerImpl(userInfoDao, converter);
    }

    @Test
    public void testGetUserInfoByUserId() {
        //arrange
        long id = 1L;
        String username = "test";
        String password = "testPassword";

        UserInfoInPersistence userInfo = UserInfoInPersistence.builder()
                .id(id)
                .username(username)
                .password(password)
                .createTime(LocalDate.now())
                .updateTime(LocalDate.now())
                .build();
        when(userInfoDao.getUserInfoByUserId(1L)).thenReturn(userInfo);

        //act
        UserInfo result = userInfoManager.getUserInfoByUserId(userInfo.getId());

        //assert
        Assertions.assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);

        verify(userInfoDao, times(1)).getUserInfoByUserId(userInfo.getId());
    }

    @Test
    public void testRegistry() {

    }
}
