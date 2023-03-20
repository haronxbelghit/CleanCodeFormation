package com.sqli.cleancodeformation.di

import android.content.Context
import androidx.room.Room
import com.sqli.cleancodeformation.data.local.dao.UserDao
import com.sqli.cleancodeformation.data.local.database.AppDatabase
import com.sqli.cleancodeformation.domain.repository.UserRepoImpl
import com.sqli.cleancodeformation.domain.repository.UserRepository
import com.sqli.cleancodeformation.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "users_db"
    )
        .allowMainThreadQueries()
    .build()


    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideLocalUserRepository(userDao: UserDao): UserRepository {
        return UserRepoImpl(
            localUserRepository = com.sqli.cleancodeformation.data.local.UserRepository(userDao)
        )
    }

    @Singleton
    @Provides
    fun provideUserRepository(userRepo: com.sqli.cleancodeformation.data.local.UserRepository): UserRepoImpl {
        return UserRepoImpl(
            localUserRepository = userRepo
        )
    }

    @Provides
    fun provideGetAllUsersUseCase(userRepository: UserRepository): GetAllUsersUseCase {
        return GetAllUsersUseCaseImpl(userRepository)
    }

    @Provides
    fun provideAddUserUseCase(userRepository: UserRepository): AddUserUseCase {
        return AddUserUseCaseImpl(userRepository)
    }

    @Provides
    fun provideGetUserDetailsUseCase(userRepository: UserRepository): GetUserDetailsUseCase {
        return GetUserDetailsUseCaseImpl(userRepository)
    }
}
