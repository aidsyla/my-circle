package com.example.androidbackend.services;


import com.example.androidbackend.dto.AllPostsDto;
import com.example.androidbackend.dto.PostsRequestDto;
import com.example.androidbackend.dto.PostsResponseDto;
import com.example.androidbackend.models.Posts;
import com.example.androidbackend.repository.PostRepository;
import com.example.androidbackend.utils.ImageUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PostsService {
    private final PostRepository postRepository;
    private String nameTemp;
    private String typeType;
    private byte[] imageData;

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getNameTemp() {
        return nameTemp;
    }

    public void setNameTemp(String nameTemp) {
        this.nameTemp = nameTemp;
    }

    public String getTypeType() {
        return typeType;
    }

    public void setTypeType(String typeType) {
        this.typeType = typeType;
    }

    public String uploadImage(MultipartFile file) throws IOException {
         Posts imageData = Posts.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build();
         setNameTemp(imageData.getName());
         setTypeType(imageData.getType());
         setImageData(imageData.getImageData());
        if (imageData != null){
            return "file uploaded successfully: " + file.getOriginalFilename();
        }
        return null;


    }

    public byte[] downloadImage(String fileName){
        Optional<Posts> dbImageData = postRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    public PostsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<PostsResponseDto> createPost(String username, String description){
        Posts newPost = new Posts();
        PostsResponseDto postsResponseDto = new PostsResponseDto();

        newPost.setUsername(username);
        newPost.setDescription(description);
        newPost.setDate(LocalDate.now());
        newPost.setName(getNameTemp());
        newPost.setType(getTypeType());
        newPost.setImageData(getImageData());
        postRepository.save(newPost);
        postsResponseDto.setUsername(newPost.getUsername());
        postsResponseDto.setDescription(newPost.getDescription());
        postsResponseDto.setDate(newPost.getDate());
        postsResponseDto.setName(newPost.getName());
        postsResponseDto.setType(newPost.getType());
        postsResponseDto.setImageData(newPost.getImageData());
        return ResponseEntity.ok(postsResponseDto);
    }

    public Posts getPostsByUsername(String username){
        return postRepository.getPostsByUsername(username).orElse(null);
    }
    public Posts getAllPostsByUsername(String username){
        return (Posts) postRepository.getAllByUsername(username).stream().toList();
    }

    public ResponseEntity<AllPostsDto> getPostByUsername(String username){
        AllPostsDto allPostsDto = new AllPostsDto();
        allPostsDto.setListOfPosts(postRepository.getPostsByUsername(username).stream().toList());
        return ResponseEntity.ok(allPostsDto);
    }

    public ResponseEntity<AllPostsDto> getAllPostByUsername(String username){
        AllPostsDto allPostsDto = new AllPostsDto();
        allPostsDto.setListOfPosts(postRepository.getAllByUsername(username).stream().toList());
        return ResponseEntity.ok(allPostsDto);
    }

    public ResponseEntity<AllPostsDto> getAllPost(){
        AllPostsDto allPostsDto = new AllPostsDto();
        allPostsDto.setListOfPosts(postRepository.findAll());
        return ResponseEntity.ok(allPostsDto);
    }




}
