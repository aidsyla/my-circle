package com.cacttuseducation.mycircle.models

data class LoginRequestDto(val email: String, val username: String, val password: String)

data class Posts(val listOfPosts: List<Post>)

data class UserResponseDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val bio: String,
    val name: String,
    val success: Boolean,
    val imageData: String
)

data class PostsResponseDto(val username: String, val description: String, val date: String)

data class Post(
    val id: Int,
    val username: String,
    val description: String,
    val date: String,
    val name: String,
    val type: String,
    val imageData: String
)

data class RegisterRequestDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String,
    val phoneNumber: String,
    val bio: String
)

data class ToPass(val username: String, val description: String)