package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserSharedViewModel : ViewModel() {

    private val _userAdded = MutableStateFlow(false)
    val userAdded: StateFlow<Boolean> = _userAdded

    fun onUserAdded() {
        _userAdded.value = true
    }
}
