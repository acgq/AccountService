package com.github.accountService.model.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoInService {
    private Long id;
    private String username;
    private String password;
}