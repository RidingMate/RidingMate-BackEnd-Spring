package com.ridingmate.api.controller;

import com.ridingmate.api.payload.AuthResponse;
import com.ridingmate.api.payload.NormalJoinRequest;
import com.ridingmate.api.payload.NormalLoginRequest;
import com.ridingmate.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/normal/join")
    @ApiOperation("일반유저(아이디, 패스워드) 회원가입")
    public ResponseEntity<AuthResponse> normalJoin(
            @Valid @RequestBody NormalJoinRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(result.getFieldErrors().get(0).getDefaultMessage()));
        }
        return ResponseEntity.ok(userService.normalJoin(request));
    }

    @PostMapping("/social/join")
    @ApiOperation("소셜 회원가입")
    public AuthResponse socialJoin() {
        return userService.socialJoin();
    }

    @PostMapping("/normal/login")
    @ApiOperation("일반유저(아이디, 패스워드) 로그인")
    public ResponseEntity normalLogin(
            @Valid @RequestBody NormalLoginRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(result.getFieldErrors().get(0).getDefaultMessage()));
        }
        return ResponseEntity.ok(userService.normalLogin(request));
    }

    @PostMapping("/social/login")
    @ApiOperation("소셜 로그인")
    public AuthResponse socialLogin() {
        return userService.socialLogin();
    }
}
