package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.data.UserDataSource

interface GetAllUsersPagingUseCase {
    operator fun invoke(): UserDataSource
}

