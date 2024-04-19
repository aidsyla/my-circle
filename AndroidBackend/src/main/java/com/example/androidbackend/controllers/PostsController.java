package com.example.androidbackend.controllers;

import com.example.androidbackend.dto.AllPostsDto;
import com.example.androidbackend.dto.PostsRequestDto;
import com.example.androidbackend.dto.PostsResponseDto;
import com.example.androidbackend.models.Posts;
import com.example.androidbackend.repository.PostRepository;
import com.example.androidbackend.services.PostsService;
import org.hibernate.mapping.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PostsController {

    private final PostsService postsService;
    private final PostRepository postRepository;
    private Posts[] List;


    public PostsController(PostsService postsService, PostRepository postRepository) {
        this.postsService = postsService;
        this.postRepository = postRepository;
    }



    @PostMapping("/post")
    public ResponseEntity<PostsResponseDto> uploadBoth(@RequestParam("image") MultipartFile file, @RequestParam("username") String username, @RequestParam("description") String description) throws IOException {

        String uploadImage = postsService.uploadImage(file);
        return postsService.createPost(username, description);
    }

    @PostMapping("/post/description")

    public ResponseEntity<PostsResponseDto> uploadDescr(@RequestBody PostsRequestDto postsRequestDto){
        return postsService.createPost(postsRequestDto.getUsername(), postsRequestDto.getDescription());
    }

    @GetMapping("/post/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData = postsService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }


    @GetMapping("/posts/{username}")
    public ResponseEntity<AllPostsDto> getPosts(@PathVariable String username){
         return postsService.getAllPostByUsername(username);

    }

    @GetMapping("/posts/all")
    public ResponseEntity<AllPostsDto> getAllPosts(){
        return postsService.getAllPost();

    }







}
