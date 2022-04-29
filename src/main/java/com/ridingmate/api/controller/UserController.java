package com.ridingmate.api.controller;

import com.ridingmate.api.payload.AuthResponse;
import com.ridingmate.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/normal/join")
    @ApiOperation("일반유저(아이디, 패스워드) 회원가입")
    public AuthResponse normalJoin() {
        return userService.normalJoin();
    }

    @PostMapping("/social/join")
    @ApiOperation("소셜 회원가입")
    public AuthResponse socialJoin() {
        return userService.socialJoin();
    }

    @PostMapping("/normal/login")
    @ApiOperation("일반유저(아이디, 패스워드) 로그인")
    public AuthResponse normalLogin() {
        return userService.normalLogin();
    }

    @PostMapping("/social/login")
    @ApiOperation("소셜 로그인")
    public AuthResponse socialLogin() {
        return userService.socialLogin();
    }
}
