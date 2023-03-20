package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.repository.UserRepository

class GetUserDetailsUseCaseImpl(private val userRepository: UserRepository) :
    GetUserDetailsUseCase {

    override suspend fun invoke(id: Int): User {
        return userRepository.getUserById(id)
    }

}
