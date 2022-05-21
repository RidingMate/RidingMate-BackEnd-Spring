package com.ridingmate.api.controller.user;

import com.ridingmate.api.dataInsert.DataInsertService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/v1/data")
@ApiIgnore
public class DataInsertController {

    @Autowired
    private DataInsertService dataInsert;
    
    @GetMapping("/insert")
    @ApiOperation("데이터 삽입")
    public void dataInsert() {
        dataInsert.jsonParse();
    }
}
