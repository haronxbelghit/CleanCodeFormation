package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.repository.UserRepository

class AddUserUseCaseImpl(private val userRepository: UserRepository) : AddUserUseCase {

    override suspend fun invoke(user: User) {
        return userRepository.addUser(user)
    }
}
