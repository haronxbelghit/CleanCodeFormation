package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.domain.model.User

interface AddUserUseCase {
    suspend operator fun invoke(user: User)
}
