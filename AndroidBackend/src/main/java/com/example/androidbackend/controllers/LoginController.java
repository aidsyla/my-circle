package com.example.androidbackend.controllers;

import com.example.androidbackend.dto.LoginRequestDto;

import com.example.androidbackend.dto.RegisterResponseDto;

import com.example.androidbackend.services.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LoginController {
    private final RegisterService registerService;

    public LoginController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/login")
    public ResponseEntity<RegisterResponseDto> loginRequest(@RequestBody LoginRequestDto loginRequestDto){
        return registerService.loginUser(loginRequestDto);
    }
}
