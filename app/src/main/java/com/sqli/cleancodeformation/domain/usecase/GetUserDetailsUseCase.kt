package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.domain.model.User

interface GetUserDetailsUseCase {

    suspend operator fun invoke(id: Int): User
}