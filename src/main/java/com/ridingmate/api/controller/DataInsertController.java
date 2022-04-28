package com.ridingmate.api.controller;

import com.ridingmate.api.dataInsert.DataInsert;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/data")
public class DataInsertController {

    @Autowired
    private DataInsert dataInsert;
    
    @GetMapping("/insert")
    @ApiOperation("데이터 삽입")
    public void adminLogin() {
        dataInsert.jsonParse();
    }
}
