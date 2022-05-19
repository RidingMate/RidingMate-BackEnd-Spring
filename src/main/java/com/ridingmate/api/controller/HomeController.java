package com.ridingmate.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@RestController
@RequestMapping
public class HomeController {

    @Autowired
    private Environment env;

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles()).findFirst().orElse("default");
    }

    @GetMapping("/swagger-ui.html")
    @ApiIgnore
    public ModelAndView redirectSwagger3() {
        return new ModelAndView("redirect:/swagger-ui/");
    }
}
