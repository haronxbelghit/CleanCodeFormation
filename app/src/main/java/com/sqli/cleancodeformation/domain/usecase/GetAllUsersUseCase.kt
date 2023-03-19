package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetAllUsersUseCase {
    suspend operator fun invoke(): Flow<List<User>>
}

