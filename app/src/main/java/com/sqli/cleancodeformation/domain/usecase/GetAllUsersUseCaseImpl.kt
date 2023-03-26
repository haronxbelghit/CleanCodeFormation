package com.sqli.cleancodeformation.domain.usecase

import com.sqli.cleancodeformation.data.UserPagingDataSource
import com.sqli.cleancodeformation.domain.repository.UserRepository

class GetAllUsersUseCaseImpl(private val userRepository: UserRepository) : GetAllUsersUseCase {
    override fun invoke(): UserPagingDataSource {
        return userRepository.getAllUsersPaged()
    }
}
