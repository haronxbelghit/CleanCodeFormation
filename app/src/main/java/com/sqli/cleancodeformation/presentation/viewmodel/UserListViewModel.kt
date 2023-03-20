package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    val userList: MutableLiveData<List<User>> = MutableLiveData()

    val selectedUserId = MutableLiveData<Int>()

    init {
        getUsers()
    }

    private fun getUsers(): MutableLiveData<List<User>> {
        val mutableLiveData = MutableLiveData<List<User>>()
        viewModelScope.launch {
            getAllUsersUseCase.invoke().collect { mutableLiveData ->
                userList.value = mutableLiveData
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
