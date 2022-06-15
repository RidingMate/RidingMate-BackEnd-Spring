package com.ridingmate.api.controller.user;

import com.ridingmate.api.payload.common.ApiResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/*
    메인 뷰 관러 컨트롤러

    상단배너, 하단배너 가져와야함
    중고거래 top8 가져와야함
*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/main")
public class MainController {


    @GetMapping("")
    @ApiOperation(value = "웹 메인 컨트롤러")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    public void webHome(){

    }

    @GetMapping("")
    @ApiOperation(value = "앱 메인 컨트롤러")
    public void appHome(){

    }

}
