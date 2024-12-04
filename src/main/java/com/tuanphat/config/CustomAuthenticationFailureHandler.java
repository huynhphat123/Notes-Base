package com.tuanphat.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component  // Thêm @Component để Spring nhận diện đây là một bean
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // Set thông báo lỗi tiếng Việt
        String errorMessage = "Thông tin đăng nhập không đúng. Vui lòng kiểm tra lại.";

        // Lưu thông báo lỗi vào session hoặc tham số request
        request.getSession().setAttribute("error", errorMessage);

        // Chuyển hướng lại trang đăng nhập với thông báo lỗi
        response.sendRedirect(request.getContextPath() + "/signin?error=true");
    }

}
