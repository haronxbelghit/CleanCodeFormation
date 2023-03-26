package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.data.UserDataSource

class GetAllUsersPagingUseCaseImpl(
    private val userDataSource: UserDataSource
) : GetAllUsersPagingUseCase {

    override fun invoke(): UserDataSource {

        return userDataSource
    }
}
