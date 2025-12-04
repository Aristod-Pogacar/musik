package com.muzik.muzik.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}

