package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.sqli.cleancodeformation.data.UserPagingDataSource
import com.sqli.cleancodeformation.domain.usecase.GetAllUsersPagingUseCase
import com.sqli.cleancodeformation.util.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getAllUsersPagingUseCase: GetAllUsersPagingUseCase,
) : ViewModel() {

    val data = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
        ),
    ) {
        UserPagingDataSource(getAllUsersPagingUseCase.invoke())
    }.flow.cachedIn(viewModelScope)


    // SingleLiveData to avoid looping in the livedata navigation and stay stuck in GetUserDetailsFragment
    val selectedUserId = SingleLiveData<Int>()


    fun onUserClicked(id: Int) {
        selectedUserId.value = id
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
