package com.ridingmate.api.controller.user;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*

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
