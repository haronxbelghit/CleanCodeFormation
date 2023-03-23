package com.sqli.cleancodeformation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sqli.cleancodeformation.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id=:id")
    fun getUserById(id: Int?): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userEntities: List<UserEntity>)
}
