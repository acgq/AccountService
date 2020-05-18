package com.github.accounting.config;

import com.github.accounting.exception.BizErrorCode;
import com.github.accounting.exception.ErrorResponse;
import com.github.accounting.exception.ServiceException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(BizErrorCode.UN_AUTHORIZED)
                .errorType(ServiceException.ErrorType.Client)
                .message("unauthorized access")
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build();
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        try (PrintWriter writer = httpServletResponse.getWriter()) {
            writer.write(new ObjectMapper().writeValueAsString(errorResponse));
        } catch (IOException ex) {
            log.debug("IOException occur when process unauthorized access");
            return false;
        }
        return false;
    }
}
