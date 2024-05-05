package com.example.circadianrhythmsync.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v${api-version}/home")
public class HomeController {

    @GetMapping("/string")
    @PreAuthorize("hasAuthority('user')")
    public String hello() {
        return "hello world";
    }
}
