package com.sqli.cleancodeformation.util

import androidx.lifecycle.MediatorLiveData

class MutableLiveList<T> : MediatorLiveData<T>() {
    fun post(value: T) {
        postValue(value)
    }
}

