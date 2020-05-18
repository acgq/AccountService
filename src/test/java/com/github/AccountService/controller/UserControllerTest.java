package com.github.AccountService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.accounting.controller.UserController;
import com.github.accounting.converter.c2s.UserInfoConverterC2S;
import com.github.accounting.exception.handler.GlobalExceptionHandler;
import com.github.accounting.manager.UserInfoManagerImpl;
import com.github.accounting.model.commom.UserInfo;
import com.github.accounting.model.service.UserInfoInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({MockitoExtension.class})
class UserControllerTest {
    @Spy
    UserInfoConverterC2S converter = new UserInfoConverterC2S();
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
        UserInfo userInfo = converter.reverse().convert(userInfoInService);
        doReturn(userInfo).when(userInfoManager).getUserInfoByUserId(id);
        //act
        mockMvc.perform(get(url + 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(userInfoInService)));
        //assert
        verify(userInfoManager).getUserInfoByUserId(anyLong());
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

    @Test
    public void testRegisterUser() throws Exception {
        //arrange
        String url = "/v1/user";
        String username = "user";
        String password = "password";
        UserInfoInService userInfo = UserInfoInService.builder()
                .username(username)
                .password(password)
                .build();
        UserInfo result = UserInfo.builder()
                .id(1L)
                .username(username)
                .password(password)
                .build();
        doReturn(result).when(userInfoManager).registerUser(username, password);

        //act
        mockMvc.perform(post(url)
                .content(new ObjectMapper().writeValueAsString(userInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(converter.convert(result))));
        verify(userInfoManager, only()).registerUser(username, password);
    }


}