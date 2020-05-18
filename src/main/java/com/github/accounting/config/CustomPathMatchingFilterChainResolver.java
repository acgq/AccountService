package com.github.accounting.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }

        String requestUri = getPathWithinApplication(request);
        return filterChainManager.getChainNames().stream()
                .filter(pattern -> isHttpRequestMatched(pattern, request, requestUri))
                .findAny()
                .map(pattern -> filterChainManager.proxy(originalChain, pattern))
                .orElse(null);
    }

    private boolean isHttpRequestMatched(String pathPattern, ServletRequest request, String requestUri) {
        String[] split = pathPattern.split("::");
        String pattern = split[0];
        boolean isHttpMethodMatched = true;
        if (split.length > 1) {
            String method = split[1].toUpperCase();
            String requestMethod = WebUtils.toHttp(request).getMethod();
            log.debug("request method is " + requestMethod);
            isHttpMethodMatched = requestMethod.equals(method);
        }
        return pathMatches(pattern, requestUri) && isHttpMethodMatched;
    }
}
