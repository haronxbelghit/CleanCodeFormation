package com.sqli.cleancodeformation

import android.app.Application
import com.sqli.cleancodeformation.data.local.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
            private set
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = AppDatabase.getInstance(this)
    }
}
