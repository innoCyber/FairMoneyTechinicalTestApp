package com.example.fairmoneytechinicaltestapp.data.datasource.remote

import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.data.model.UserResponse
import com.example.fairmoneytechinicaltestapp.util.UIStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


class FetchRemoteDataImpl @Inject constructor(
    private val apiService: APIService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

): FetchRemoteData{
    override suspend fun getAllUsers(): UIStatus<UserResponse?> = withContext(ioDispatcher) {
        return@withContext try {
            val response = apiService.getAllUsers()
            if (response.isSuccessful){
                UIStatus.success(response.body())
            }else{
                UIStatus.error(response.errorBody().toString(), null)
            }
        }catch (e: Exception){
            UIStatus.error(e.message!!, null)
        }catch (IO: Exception){
            UIStatus.error("Something went wrong", null)
        }
    }

    override suspend fun getUserDetails(id: String): UIStatus<User?> = withContext(ioDispatcher){
        return@withContext try {
            val response = apiService.getUserDetails(id)
            if (response.isSuccessful){
                UIStatus.success(response.body())
            }else{
                UIStatus.error(response.errorBody().toString(), null)
            }
        }catch (e: Exception){
            UIStatus.error(e.message!!, null)
        }catch (IO: Exception){
            UIStatus.error("Something went wrong", null)
        }
    }
}