package com.cacttuseducation.mycircle.api

import com.cacttuseducation.mycircle.models.*
import retrofit2.Call
import retrofit2.http.*

interface API {

    @POST("login")
    fun requestLogin(@Body login: Login): Call<UserResponseDto>

    @GET("posts/all")
    fun getPosts(): Call<Posts>

    @POST("post/description")
    fun newPost(@Body toPass: ToPass): Call<PostsResponseDto>

    @GET("posts/{username}")
    fun getPostsByUsername(@Path("username") username: String): Call<Posts>

    @POST("register")
    fun requestRegister(@Body register: RegisterRequestDto): Call<UserResponseDto>

}