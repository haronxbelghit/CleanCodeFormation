package com.sqli.cleancodeformation.data.local.callback

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sqli.cleancodeformation.data.local.dao.UserDao
import com.sqli.cleancodeformation.data.local.entity.UserEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDatabaseCallback(private val userDao: UserDao) : RoomDatabase.Callback() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Insert initial data
        GlobalScope.launch {
            val user = UserEntity(0, "John", "")
            userDao.insert(user)
        }
    }
}
