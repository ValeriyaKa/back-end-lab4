package com.example.weblab4.Controller;

import com.example.weblab4.AService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AController {
    private final AService authService;

    @Autowired
    public AController(AService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/register")
    public void register(@RequestParam("login") String login, @RequestParam("password") String password) {
        authService.register(login, password);
    }

    @PostMapping("/api/login")
    public void login(@RequestHeader("Authorization") String authorization) {
        authService.check(authorization);
    }
}