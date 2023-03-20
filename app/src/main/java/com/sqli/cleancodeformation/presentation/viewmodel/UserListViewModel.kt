package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.usecase.GetAllUsersUseCase
import com.sqli.cleancodeformation.util.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {


    private val _userList = MutableLiveData<List<User>>()
    val userList: MutableLiveData<List<User>> = _userList

    // SingleLiveData to avoid looping in the livedata navigation and stay stuck in userdetailfrag
    val selectedUserId =  SingleLiveData<Int>()

    init {
        getUsers()
    }

    private fun getUsers(): MutableLiveData<List<User>> {
        val mutableLiveData = MutableLiveData<List<User>>()
        viewModelScope.launch {
            getAllUsersUseCase.invoke().collect { mutableLiveData ->
                _userList.value = mutableLiveData
            }
        }
        return mutableLiveData
    }

    fun onUserClicked(id: Int) {
        selectedUserId.value = id
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
