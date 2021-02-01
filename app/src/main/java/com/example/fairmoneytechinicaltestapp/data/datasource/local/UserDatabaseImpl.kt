package com.example.fairmoneytechinicaltestapp.data.datasource.local

import androidx.lifecycle.LiveData
import com.example.fairmoneytechinicaltestapp.data.datasource.local.dao.UserDao
import com.example.fairmoneytechinicaltestapp.data.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UserDatabaseImpl @Inject constructor(
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

): LocalDatabase {

    override suspend fun saveUsers(users: List<User>) = withContext(ioDispatcher) {
        userDao.insertAll(users)
    }

    override fun getUsers(): LiveData<List<User>> = userDao.getAllUsers()

    override suspend fun saveUserDetail(user: User) = withContext(ioDispatcher) {
        userDao.insert(user)
    }

    override fun getUserDetail(id :String) : LiveData<User> = userDao.getUserDetail(id)


}