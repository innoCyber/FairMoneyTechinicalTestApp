package com.example.fairmoneytechinicaltestapp.data.datasource.remote

import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.data.model.UserResponse
import com.example.fairmoneytechinicaltestapp.util.UIStatus

interface FetchRemoteData  {

    suspend fun getAllUsers(): UIStatus<UserResponse?>

    suspend fun getUserDetails(id :String): UIStatus<User?>
}
