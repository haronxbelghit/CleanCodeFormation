package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.usecase.GetAllUsersUseCase
import com.sqli.cleancodeformation.presentation.adapter.UserListAdapter
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

    init {
        getUsers()
    }

    @BindingAdapter("userList")
    fun setUsers(recyclerView: RecyclerView, userList: MutableLiveData<List<User>>) {
        userList.value?.let {
            recyclerView.adapter = UserListAdapter(getUsers())
        }
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


//    private fun getUsers(): MutableLiveData<List<User>> {
//        val mutableLiveData = MutableLiveData<List<User>>()
//        viewModelScope.launch {
//            val result = getAllUsersUseCase.invoke()
//            mutableLiveData.value = result
//        }
//        return mutableLiveData
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
