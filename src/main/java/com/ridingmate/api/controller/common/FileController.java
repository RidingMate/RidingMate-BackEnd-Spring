package com.ridingmate.api.controller.common;

import com.ridingmate.api.payload.common.FileResult;
import com.ridingmate.api.service.common.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/v1/file")
@Controller
public class FileController {

    private final FileService fileService;

//    @PostMapping("/upload")
//    @ResponseBody
//    public FileResult upload(@RequestPart MultipartFile multipartFile) throws Exception {
//        return fileService.uploadFile(multipartFile,"test", "kang");
//    }
}
