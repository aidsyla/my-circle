package com.example.androidbackend.services;

import com.example.androidbackend.dto.LoginRequestDto;
import com.example.androidbackend.dto.RegisterRequestDto;
import com.example.androidbackend.dto.RegisterResponseDto;
import com.example.androidbackend.models.Posts;
import com.example.androidbackend.models.Register;
;
import com.example.androidbackend.repository.RegisterRepository;
import com.example.androidbackend.utils.ImageUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class RegisterService {

    private final RegisterRepository registerRepository;

    private final PostsService postsService;


    public RegisterService(RegisterRepository registerRepository, PostsService postsService) {
        this.registerRepository = registerRepository;
        this.postsService = postsService;
    }

    public Register getUserByEmailOrUsername(String email, String username) {
        return registerRepository.findByEmailOrUsername(email, username).orElse(null);
    }


    public ResponseEntity<RegisterResponseDto> createUser(RegisterRequestDto registerRequestDto) {
        Register register = getUserByEmailOrUsername(registerRequestDto.getEmail(), registerRequestDto.getUsername());

        Register newUser = new Register();

        RegisterResponseDto registerResponseDto = new RegisterResponseDto();

        if (register != null) {
            registerResponseDto.setMessage("User exist!");
        } else {
            newUser.setFirstName(registerRequestDto.getFirstName());
            newUser.setLastName(registerRequestDto.getLastName());
            newUser.setEmail(registerRequestDto.getEmail());
            newUser.setUsername(registerRequestDto.getUsername());
            newUser.setPassword(registerRequestDto.getPassword());
            newUser.setBio(registerRequestDto.getBio());
            newUser.setPhoneNumber(registerRequestDto.getPhoneNumber());
            registerRepository.save(newUser);
            registerResponseDto.setFirstName(registerRequestDto.getFirstName());
            registerResponseDto.setLastName(registerRequestDto.getLastName());
            registerResponseDto.setEmail(registerRequestDto.getEmail());
            registerResponseDto.setUsername(registerRequestDto.getUsername());
            registerResponseDto.setBio(newUser.getBio());
            registerResponseDto.setName(newUser.getName());
            registerResponseDto.setType(newUser.getType());
            registerResponseDto.setImageData(newUser.getImageData());
            registerResponseDto.setSuccess(true);
            registerResponseDto.setMessage("You have successfully registered!");

        }

        return ResponseEntity.ok(registerResponseDto);
    }

    public Register getUserByUsername(String username) {
        return registerRepository.findByUsername(username).orElse(null);
    }


    public ResponseEntity<String> uploadProfilePic(MultipartFile file, String username, String bio) {
        Register register = getUserByUsername(username);
        try {
            postsService.uploadImage(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        register.setName(file.getOriginalFilename());
        register.setType(file.getContentType());
        register.setBio(bio);
        try {
            register.setImageData(ImageUtils.compressImage(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerRepository.save(register);

        return ResponseEntity.ok("Profile picture is added successfully!");
    }

    public ResponseEntity<RegisterResponseDto> loginUser(LoginRequestDto loginRequestDto) {
        Register loginUser = getUserByEmailOrUsername(loginRequestDto.getEmail(), loginRequestDto.getUsername());

        RegisterResponseDto registerResponseDto = new RegisterResponseDto();

        if (loginUser != null) {
            if (loginUser.getPassword().equals(loginRequestDto.getPassword())) {
                registerResponseDto.setFirstName(loginUser.getFirstName());
                registerResponseDto.setLastName(loginUser.getLastName());
                registerResponseDto.setEmail(loginUser.getEmail());
                registerResponseDto.setUsername(loginUser.getUsername());
                registerResponseDto.setBio(loginUser.getBio());
                registerResponseDto.setName(loginUser.getName());
                registerResponseDto.setType(loginUser.getType());
                registerResponseDto.setImageData(loginUser.getImageData());
                registerResponseDto.setSuccess(true);
                registerResponseDto.setMessage("You are Logged in!");
            } else {
                registerResponseDto.setMessage("Provided credentials are not OK!");
            }
        } else {
            registerResponseDto.setMessage("Provided credentials are not OK!");
        }
        return ResponseEntity.ok(registerResponseDto);
    }


    public byte[] downloadImage(String fileName){
        Optional<Register> dbImageData = registerRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    public Register getByName(String name){
        return registerRepository.findByName(name).orElse(null);
    }


}
