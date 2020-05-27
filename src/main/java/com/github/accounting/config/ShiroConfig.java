package com.github.accounting.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;

@Configuration
public class ShiroConfig {

    public static final int HASH_ITERATIONS = 1024;
    public static final String HASH_ALGORITHM_NAME = "SHA-256";

    @Bean
    public SecurityManager getSecurityManager(Realm realm) {
        SecurityManager securityManager = new DefaultWebSecurityManager(realm);
        return securityManager;
    }

    /**
     * get shiro filter factory bean.
     *
     * @param securityManager securityManager
     * @return bean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //add custom filter
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("custom", new CustomHttpFilter());
        filters.put("authc", new CustomFormAuthenticationFilter());

        Map<String, String> shiroFilterDefinitionMap = new LinkedHashMap<>();
        shiroFilterDefinitionMap.put("/v1/user/**::post", "custom");
        shiroFilterDefinitionMap.put("/v1/session/**", "anon");
        shiroFilterDefinitionMap.put("/**", "authc");

        //swagger related url.
        shiroFilterDefinitionMap.put("/swagger-ui.html/**", "anon");
        shiroFilterDefinitionMap.put("/swagger-resources/**", "anon");
        shiroFilterDefinitionMap.put("/v2/**", "anon");
        shiroFilterDefinitionMap.put("/webjars/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * get CredentialMatcher bean.
     *
     * @return bean instance
     */
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(HASH_ALGORITHM_NAME);
        matcher.setHashIterations(HASH_ITERATIONS);
        matcher.setHashSalted(true);
        matcher.setStoredCredentialsHexEncoded(false);
        return matcher;
    }
}
