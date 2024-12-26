package com.example.financeReportSystem.controller;

import com.example.financeReportSystem.dto.LoginRequest;
import com.example.financeReportSystem.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 模拟用户名和密码验证
        String validUsername = "testuser";
        String validPassword = "123456";

        if (validUsername.equals(loginRequest.getUsername()) && validPassword.equals(loginRequest.getPassword())) {
            // 模拟成功的登录响应
            return ResponseEntity.ok(new LoginResponse("登录成功", true));
        } else {
            // 模拟失败的登录响应
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("用户名或密码错误", false));
        }
    }
}
