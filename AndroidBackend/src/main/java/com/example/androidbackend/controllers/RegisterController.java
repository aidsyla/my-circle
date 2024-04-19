package com.example.androidbackend.controllers;

import com.example.androidbackend.dto.RegisterRequestDto;
import com.example.androidbackend.dto.RegisterResponseDto;
import com.example.androidbackend.services.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RegisterController {

    private final RegisterService registerService;


    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto){
        return registerService.createUser(registerRequestDto);
    }

    @PostMapping("/upload/picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("picture")MultipartFile file, @RequestParam("username")String username, @RequestParam("bio")String bio){
        registerService.uploadProfilePic(file,username, bio);
        return ResponseEntity.ok("Profile picture is added successfully!");
    }

    @GetMapping("/post/picture/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData = registerService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
