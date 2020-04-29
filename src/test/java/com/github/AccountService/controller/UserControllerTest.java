package com.github.AccountService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.accountService.controller.UserController;
import com.github.accountService.converter.c2s.UserInfoConverterC2S;
import com.github.accountService.dao.mapper.UserInfoMapper;
import com.github.accountService.exception.handler.GlobalExceptionHandler;
import com.github.accountService.manager.UserInfoManager;
import com.github.accountService.manager.UserInfoManagerImpl;
import com.github.accountService.model.commom.UserInfo;
import com.github.accountService.model.service.UserInfoInService;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith({MockitoExtension.class})
class UserControllerTest {
    @Mock
    UserInfoConverterC2S converter;
    @Mock
    UserInfoManagerImpl userInfoManager;
    @InjectMocks
    UserController userController;

    MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetUserInfoById() throws Exception {
        //arrange
        String url = "/v1/user/";
        long id = 1L;
        String username = "test";
        String password = "testPassword";
        UserInfoInService userInfoInService = UserInfoInService.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();
        UserInfo userInfo = UserInfo.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();
        doReturn(userInfo).when(userInfoManager).getUserInfoByUserId(id);
        doReturn(userInfoInService).when(converter).convert(userInfo);
        //act
        mockMvc.perform(get(url + 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("{\"id\":1,\"username\":\"test\",\"password\":\"testPassword\"}"));
        //assert
        verify(userInfoManager).getUserInfoByUserId(anyLong());
        verify(converter, only()).convert(userInfo);
    }

    @Test
    public void testGetUserInfoByIdWithInvalidParameter() throws Exception {
        String url = "/v1/user/";
        long id = -10L;
        mockMvc.perform(get(url + id))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(String.format("{\"statusCode\":400,\"errorCode\":\"INVALID_PARAMETER\",\"errorType\":\"Client\",\"message\":\"invalid user id %s\"}", id)));
        verify(userInfoManager, never()).getUserInfoByUserId(anyLong());
    }

}