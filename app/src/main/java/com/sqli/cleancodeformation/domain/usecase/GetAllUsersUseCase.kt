package com.sqli.cleancodeformation.domain.usecase

import androidx.lifecycle.LiveData
import com.sqli.cleancodeformation.domain.model.User

interface GetAllUsersUseCase {
    suspend operator fun invoke(): LiveData<List<User>>
}

