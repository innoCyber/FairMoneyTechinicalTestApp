package com.example.fairmoneytechinicaltestapp.data.datasource.remote

import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val appId = "app-id: 6013e314ab6b9e1080a8b562"
interface APIService {
    @Headers(appId)
    @GET("user")
    suspend fun getAllUsers(): Response<UserResponse>

    @Headers(appId)
    @GET("user/{id}")
    suspend fun getUserDetails(@Path("id") id:String): Response<User>
}