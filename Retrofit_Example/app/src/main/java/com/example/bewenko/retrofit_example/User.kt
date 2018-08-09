package com.example.bewenko.retrofit_example

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("login") val login: String,
        @SerializedName("id") val id: Int,
        @SerializedName("url") val url: String)
