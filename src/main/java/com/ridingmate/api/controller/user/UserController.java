package com.ridingmate.api.controller.user;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ridingmate.api.annotation.CurrentUser;
import com.ridingmate.api.payload.common.AuthResponse;
import com.ridingmate.api.payload.common.ResponseDto;
import com.ridingmate.api.payload.user.dto.NormalUserDto;
import com.ridingmate.api.payload.user.dto.UserDto;
import com.ridingmate.api.payload.user.dto.UserDto.Request.Update;
import com.ridingmate.api.payload.user.dto.UserDto.Response.Count;
import com.ridingmate.api.payload.user.dto.UserDto.Response.Info;
import com.ridingmate.api.security.UserPrincipal;
import com.ridingmate.api.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import springfox.documentation.annotations.ApiIgnore;

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

    @SneakyThrows
    @GetMapping("/info")
    @ApiOperation("유저 정보 조회")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<UserPrincipal.Info> getUserInfo(
            @ApiIgnore @CurrentUser UserPrincipal user
    ) {
        return ResponseDto.<UserPrincipal.Info>builder()
                          .response(user.getInfo())
                          .build();
    }

    @SneakyThrows
    @PostMapping("/board/count")
    @ApiOperation("게시글, 댓글, 북마크 카운트")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<UserDto.Response.Count> getMyBoardCount(
            @ApiIgnore @CurrentUser UserPrincipal user
    ) {
        return ResponseDto.<Count>builder()
                          .response(userService.getBoardCount(user.getUser()))
                          .build();
    }

    @SneakyThrows
    @PostMapping("/info")
    @ApiOperation("회원 정보 수정")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<UserDto.Response.Info> updateUserInfo(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @RequestBody Update dto
    ) {
        return ResponseDto.<Info>builder()
                          .response(userService.updateUserInfo(dto, user.getUser()))
                          .build();
    }

}
