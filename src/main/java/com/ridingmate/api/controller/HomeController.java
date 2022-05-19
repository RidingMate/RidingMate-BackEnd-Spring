package com.ridingmate.api.controller;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final Environment env;

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles()).findFirst().orElse("default");
    }
}
