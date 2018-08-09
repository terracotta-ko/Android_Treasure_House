package com.example.bewenko.retrofit_example

import retrofit2.Call
import retrofit2.http.GET

interface UserApi {

    @GET("/users")
    fun getUsers(): Call<List<User>>
}