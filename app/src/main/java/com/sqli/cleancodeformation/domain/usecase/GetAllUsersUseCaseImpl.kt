package com.sqli.cleancodeformation.domain.usecase


import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCaseImpl(private val userRepository: UserRepository) : GetAllUsersUseCase {
    override suspend fun invoke(): Flow<List<User>> {
        return userRepository.getAllUsers()
    }
}
