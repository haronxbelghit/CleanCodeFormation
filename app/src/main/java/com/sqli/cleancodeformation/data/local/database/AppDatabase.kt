package com.sqli.cleancodeformation.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sqli.cleancodeformation.data.local.dao.UserDao
import com.sqli.cleancodeformation.data.local.entity.UserEntity


@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "users_db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!
        }

    }

}
