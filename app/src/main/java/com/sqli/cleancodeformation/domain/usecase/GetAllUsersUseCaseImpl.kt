package com.sqli.cleancodeformation.domain.usecase


import androidx.lifecycle.LiveData
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.repository.UserRepository

class GetAllUsersUseCaseImpl(private val userRepository: UserRepository) : GetAllUsersUseCase {
    override suspend fun invoke(): LiveData<List<User>> {
        return userRepository.getAllUsers()
    }
}
