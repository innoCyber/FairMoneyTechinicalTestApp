package com.example.fairmoneytechinicaltestapp.ui.userdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fairmoneytechinicaltestapp.data.datasource.repository.UserRepository
import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.util.UIStatus


class UserDetailViewModel @ViewModelInject constructor(
    private val repository: UserRepository
): ViewModel(){

    private var _id = MutableLiveData<String>()

    fun userFromLocal(id: String) : LiveData<User> = repository.getUserDetailFromLocal(id)


    fun getUserFromRemote(id: String): LiveData<UIStatus<Unit>> {

        return repository.getUserDetailFromRemote(id)

    }

    fun getUserDetail(id: String) {
        _id.value = id
    }

}