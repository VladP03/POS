package com.POS.Book.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class ValidateTokenInterception implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        ValidateToken validateToken = new ValidateToken();

        validateToken.validateToken(token);

        return true;
    }
}
