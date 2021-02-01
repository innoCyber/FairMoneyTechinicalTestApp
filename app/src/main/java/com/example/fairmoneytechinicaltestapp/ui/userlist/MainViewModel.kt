package com.example.fairmoneytechinicaltestapp.ui.userlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.fairmoneytechinicaltestapp.data.datasource.repository.UserRepository


class MainViewModel @ViewModelInject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _userList = MutableLiveData<Unit>()

    fun getAllUsers() {
        _userList.value = Unit
    }

    val fetchAllUsers = _userList.switchMap {
        repository.getAllUsersFromRemote()
    }

    val userList = repository.getAllUsersFromLocal()
}