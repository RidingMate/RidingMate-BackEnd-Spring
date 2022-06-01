package com.ridingmate.api.controller.user;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ridingmate.api.payload.common.AuthResponse;
import com.ridingmate.api.payload.user.request.NormalJoinRequest;
import com.ridingmate.api.payload.user.request.NormalLoginRequest;
import com.ridingmate.api.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @SneakyThrows
    @PostMapping("/normal/join")
    @ApiOperation("일반유저(아이디, 패스워드) 회원가입")
    public ResponseEntity normalJoin(
            @Valid @RequestBody NormalJoinRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(userService.normalJoin(request));
    }

    @PostMapping("/social/join")
    @ApiOperation("소셜 회원가입")
    public AuthResponse socialJoin() {
        return userService.socialJoin();
    }

    @SneakyThrows
    @PostMapping("/normal/login")
    @ApiOperation("일반유저(아이디, 패스워드) 로그인")
    public ResponseEntity normalLogin(
            @Valid @RequestBody NormalLoginRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(userService.normalLogin(request));
    }

    @PostMapping("/social/login")
    @ApiOperation("소셜 로그인")
    public AuthResponse socialLogin() {
        return userService.socialLogin();
    }
}
