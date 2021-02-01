package com.example.fairmoneytechinicaltestapp.data.datasource.local

import androidx.lifecycle.LiveData
import com.example.fairmoneytechinicaltestapp.data.model.User

interface LocalDatabase {
    fun getUsers(): LiveData<List<User>>
    fun getUserDetail(id :String): LiveData<User>
    suspend fun saveUsers(users: List<User>)
    suspend fun saveUserDetail(user: User)
}