package com.github.accounting.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;

@Slf4j
public class CustomHttpFilter extends PathMatchingFilter {

    /**
     * custom path filter.
     *
     * @param path    path in config.
     * @param request request.
     * @return is path matched
     */
    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String currentPath = getPathWithinApplication(request);
        log.debug("Path in CustomHttpFilter : {},currentPath :{}", path, currentPath);
        String[] array = path.split("::");
        String url = array[0];
        boolean isHttpMethodMatched = true;
        if (array.length > 1) {
            String methodInRequest = WebUtils.toHttp(request).getMethod().toUpperCase();
            String method = array[1].toUpperCase();
            isHttpMethodMatched = method.equals(methodInRequest);
        }
        return pathsMatch(url, currentPath) && isHttpMethodMatched;
    }
}
