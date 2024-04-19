package com.example.androidbackend.dto;

import com.example.androidbackend.models.Posts;

import java.util.List;

public class AllPostsDto {
    private List<Posts> listOfPosts;

    public List<Posts> getListOfPosts() {
        return listOfPosts;
    }

    public void setListOfPosts(List<Posts> listOfPosts) {
        this.listOfPosts = listOfPosts;
    }
}
