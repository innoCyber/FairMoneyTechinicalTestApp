package com.example.fairmoneytechinicaltestapp.data.datasource.repository

import androidx.lifecycle.LiveData
import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.util.UIStatus

interface UserRepository {

    suspend fun saveUserDetail(id: String): LiveData<User>
    suspend fun saveAllUsers(user: List<User>)

    fun getUserDetailFromRemote(id:String): LiveData<UIStatus<Unit>>
    fun getAllUsersFromRemote(): LiveData<UIStatus<Unit>>

    fun getUserDetailFromLocal(id: String): LiveData<User>
    fun getAllUsersFromLocal(): LiveData<List<User>>
}