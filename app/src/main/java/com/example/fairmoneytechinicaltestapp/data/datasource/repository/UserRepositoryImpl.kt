package com.example.fairmoneytechinicaltestapp.data.datasource.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.fairmoneytechinicaltestapp.data.datasource.local.LocalDatabase
import com.example.fairmoneytechinicaltestapp.data.datasource.remote.FetchRemoteData
import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.util.UIStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: FetchRemoteData,
    private val localDataSource: LocalDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): UserRepository {

    override suspend fun saveAllUsers(user: List<User>) {
        localDataSource.saveUsers(user)
    }

    override fun getAllUsersFromRemote(): LiveData<UIStatus<Unit>> = liveData(ioDispatcher){
        val response = remoteDataSource.getAllUsers()
        when(response.status){
            UIStatus.Status.LOADING -> {
                emit(UIStatus.loading((Unit)))
            }

            UIStatus.Status.SUCCESS -> {
                response.data.let {
                    localDataSource.saveUsers(it?.data!!)
                }
                emit(UIStatus.success(Unit))
            }

            UIStatus.Status.ERROR -> {
                emit(UIStatus.error(response.message!!, null))
            }
        }
    }

    override fun getAllUsersFromLocal(): LiveData<List<User>> = localDataSource.getUsers()


    override suspend fun saveUserDetail(id: String): LiveData<User> {
        return  localDataSource.getUserDetail(id)
    }

    override fun getUserDetailFromRemote(id: String): LiveData<UIStatus<Unit>> = liveData(ioDispatcher) {
        val response = remoteDataSource.getUserDetails(id)
        when(response.status){
            UIStatus.Status.LOADING -> {
                emit(UIStatus.loading((Unit)))
            }

            UIStatus.Status.SUCCESS -> {
                response.data.let {
                    localDataSource.saveUserDetail(it!!)
                }
                emit(UIStatus.success(Unit))
            }

            UIStatus.Status.ERROR -> {
                emit(UIStatus.error(response.message!!, null))
            }
        }
    }

    override fun getUserDetailFromLocal(id: String): LiveData<User> {
        return localDataSource.getUserDetail(id)
    }


}