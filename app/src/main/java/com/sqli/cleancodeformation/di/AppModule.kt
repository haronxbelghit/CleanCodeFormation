package com.sqli.cleancodeformation.di

import android.content.Context
import androidx.room.Room
import com.sqli.cleancodeformation.data.UserDataSource
import com.sqli.cleancodeformation.data.UserDataSourceImpl
import com.sqli.cleancodeformation.data.UserPagingDataSource
import com.sqli.cleancodeformation.data.local.database.AppDatabase
import com.sqli.cleancodeformation.data.remote.UserRemoteDataSource
import com.sqli.cleancodeformation.data.remote.UserRemoteDataSourceImpl
import com.sqli.cleancodeformation.data.remote.api.UserApiService
import com.sqli.cleancodeformation.domain.repository.UserRepoImpl
import com.sqli.cleancodeformation.domain.repository.UserRepository
import com.sqli.cleancodeformation.domain.usecase.* // ktlint-disable no-wildcard-imports
import com.sqli.cleancodeformation.presentation.viewmodel.UserSharedViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "users_db",
    )
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideApiService(): UserApiService {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDatasource(
        userRemoteDataSource: UserRemoteDataSource,
        userEntityRepository: com.sqli.cleancodeformation.data.local.UserRepository,
    ): UserDataSource {
        return UserDataSourceImpl(userRemoteDataSource, userEntityRepository)
    }

    @Singleton
    @Provides
    fun provideUserRemoteDatasource(apiService: UserApiService): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideRepository(
        apiService: UserRemoteDataSource,
        userEntityRepository: com.sqli.cleancodeformation.data.local.UserRepository,
    ): UserDataSourceImpl {
        return UserDataSourceImpl(apiService, userEntityRepository)
    }

    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideUserRepository(
        userDataSource: UserDataSource,
    ): UserRepository {
        return UserRepoImpl(
            userDataSource,
        )
    }

    @Singleton
    @Provides
    fun provideYourSharedVM(): UserSharedViewModel {
        return UserSharedViewModel()
    }

    @Provides
    fun provideGetAllUsersUseCase(userRepository: UserRepository): GetAllUsersUseCase {
        return GetAllUsersUseCaseImpl(userRepository)
    }

    @Provides
    fun provideUserPagingSource(userDataSource: UserDataSource): UserPagingDataSource {
        return UserPagingDataSource(userDataSource)
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
