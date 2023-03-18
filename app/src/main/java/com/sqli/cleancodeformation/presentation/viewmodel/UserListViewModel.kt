package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.usecase.AddUserUseCase
import com.sqli.cleancodeformation.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addUserUseCase: AddUserUseCase
) :
    ViewModel() {

    private val _userList = MutableLiveData<List<User>>()

    val userList: MutableLiveData<List<User>>
        get() = _userList

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            val users = getAllUsersUseCase.invoke()
            _userList.value = users.value
        }
    }

}
