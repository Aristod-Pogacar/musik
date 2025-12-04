package com.muzik.muzik;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @GetMapping("/")
    public String redirectToMusic() {
        return "redirect:/music";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
