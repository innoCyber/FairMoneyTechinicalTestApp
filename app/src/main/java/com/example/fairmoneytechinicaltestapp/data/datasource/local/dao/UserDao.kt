package com.example.fairmoneytechinicaltestapp.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fairmoneytechinicaltestapp.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserDetail(id: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Delete
    fun deleteAll(user: List<User>)

    @Delete
    fun delete(user:User)

}