package com.github.accounting.config;

import com.github.accounting.manager.UserInfoManager;
import com.github.accounting.model.commom.UserInfo;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRealm extends AuthorizingRealm {
    private final UserInfoManager userInfoManager;


    @Autowired
    public UserRealm(UserInfoManager userInfoManager, HashedCredentialsMatcher matcher) {
        super(matcher);
        this.userInfoManager = userInfoManager;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        UserInfo userInfo = userInfoManager.getUserInfoByUsername(username);

        return new SimpleAuthenticationInfo(
                username,
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getSalt()),
                this.getName());
    }
}
