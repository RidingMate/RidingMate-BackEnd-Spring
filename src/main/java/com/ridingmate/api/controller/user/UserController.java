package com.ridingmate.api.controller.user;

import javax.validation.Valid;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ridingmate.api.payload.common.AuthResponse;
import com.ridingmate.api.payload.common.ResponseDto;
import com.ridingmate.api.payload.user.dto.NormalUserDto;
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
    public ResponseDto<AuthResponse> normalJoin(
            @Valid @RequestBody NormalUserDto.Request.Join request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseDto.<AuthResponse>builder()
                          .response(userService.normalJoin(request))
                          .build();
    }

    @PostMapping("/social/join")
    @ApiOperation("소셜 회원가입")
    public ResponseDto<AuthResponse> socialJoin() {
        return ResponseDto.<AuthResponse>builder()
                          .response(userService.socialJoin())
                          .build();
    }

    @SneakyThrows
    @PostMapping("/normal/login")
    @ApiOperation("일반유저(아이디, 패스워드) 로그인")
    public ResponseDto<AuthResponse> normalLogin(
            @Valid @RequestBody NormalUserDto.Request.Login request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseDto.<AuthResponse>builder()
                          .response(userService.normalLogin(request))
                          .build();
    }

    @PostMapping("/social/login")
    @ApiOperation("소셜 로그인")
    public ResponseDto<AuthResponse> socialLogin() {
        return ResponseDto.<AuthResponse>builder()
                          .response(userService.socialLogin())
                          .build();
    }
}
