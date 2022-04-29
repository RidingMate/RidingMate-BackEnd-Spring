package com.ridingmate.api.controller;

import com.ridingmate.api.entity.value.UserRole;
import com.ridingmate.api.payload.AuthResponse;
import com.ridingmate.api.payload.BikeCompanyDto;
import com.ridingmate.api.payload.NormalJoinRequest;
import com.ridingmate.api.payload.NormalLoginRequest;
import com.ridingmate.api.service.BikeService;
import com.ridingmate.api.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/bike")
public class BikeController {

    private final BikeService bikeService;

    @GetMapping("/search/company")
    @ApiOperation(value = "바이크 등록 - 제조사 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 500, message = "")
    })
    @PreAuthorize("hasRole('USER')")
    public List<BikeCompanyDto> searchCompany(
            @RequestHeader(value = "Authorization") String token
    ){
        return bikeService.searchCompany();
    }


}
