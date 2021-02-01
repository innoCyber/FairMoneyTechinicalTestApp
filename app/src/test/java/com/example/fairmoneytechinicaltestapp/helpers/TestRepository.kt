package com.example.fairmoneytechinicaltestapp.helpers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.fairmoneytechinicaltestapp.data.datasource.repository.UserRepository
import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.util.UIStatus

class TestRepository : UserRepository {

    private var shouldReturnError = false

    private val data = mutableListOf<User>()
    private val user = TestHelperObject.user

    private val observeData = MutableLiveData<List<User>>()
    private val observeUser = MutableLiveData<User>()

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    private fun refreshData() {
        observeData.value = data
        observeUser.value = user
    }

    override suspend fun saveAllUsers(data: List<User>) {
        this.data.addAll(data)
        refreshData()
    }

    override fun getAllUsersFromRemote(): LiveData<UIStatus<Unit>> {
        return liveData {
            val response = if (shouldReturnError) {
                UIStatus.error("Something went wrong", null)
            } else {
                UIStatus.success(TestHelperObject.userList)
            }

            when (response.status) {
                UIStatus.Status.SUCCESS -> {
                    response.data?.let {
                        saveAllUsers(it)
                    }
                    emit(UIStatus.success(Unit))
                }
                UIStatus.Status.ERROR -> {
                    emit(UIStatus.error(response.message!!, null))
                }
                else -> {
                }
            }
        }
    }

    override fun getAllUsersFromLocal(): LiveData<List<User>> {
        return observeData
    }

    override suspend fun saveUserDetail(id: String): LiveData<User> {
        return  observeUser
    }

    override fun getUserDetailFromRemote(id: String): LiveData<UIStatus<Unit>> {
        return liveData {
            val response = if (shouldReturnError) {
                UIStatus.error("Something went wrong", null)
            } else {
                UIStatus.success(TestHelperObject.userList)
            }

            when (response.status) {
                UIStatus.Status.SUCCESS -> {
                    response.data?.let {
                        saveUserDetail(id)
                    }
                    emit(UIStatus.success(Unit))
                }
                UIStatus.Status.ERROR -> {
                    emit(UIStatus.error(response.message!!, null))
                }
                else -> {
                }
            }
        }
    }

    override fun getUserDetailFromLocal(id: String): LiveData<User> {
        return  observeUser
    }

}
