package com.project.ordermanagementsystems.controller;

import com.project.ordermanagementsystems.controller.request.AuthRequest;
import com.project.ordermanagementsystems.controller.request.RegisterRequest;
import com.project.ordermanagementsystems.service.UserService;
import com.project.ordermanagementsystems.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class HomeController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {
        String jwt = userService.login(loginRequest);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.signup(registerRequest);
        return ResponseEntity.ok("You have registered successfully");
    }
}