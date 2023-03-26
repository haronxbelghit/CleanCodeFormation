package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.data.UserPagingDataSource

interface GetAllUsersUseCase {
    operator fun invoke(): UserPagingDataSource
}

