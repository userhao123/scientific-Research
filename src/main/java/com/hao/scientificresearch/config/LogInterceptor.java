package com.hao.scientificresearch.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        if (request.getSession().getAttribute("loginUser") == null) {
            request.getRequestDispatcher("/page/login").forward(request, response);
//            response.sendRedirect("/login");
            return false;
        }
        //已登录，直接放行
        return true;
    }
}
