package com.ko.user_database

import android.content.Context
import androidx.room.Room

class UserLocalDataSourceProvider private constructor(context: Context) {

    private val userRoomDatabase: UserRoomDatabase by lazy {
        Room.databaseBuilder(
            context,
            UserRoomDatabase::class.java,
            TABLE_NAME
        ).build()
    }

    val localDataSource: UserLocalDataSource by lazy {
        UserLocalDataSourceDefault(
            userRoomDatabase.userDao(),
            UserEntityMapperDefault(),
            UserRoomEntityMapperDefault()
        )
    }

    companion object {

        @Volatile
        private var INSTANCE: UserLocalDataSourceProvider? = null

        fun getInstance(context: Context): UserLocalDataSourceProvider {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserLocalDataSourceProvider(context).also { INSTANCE = it }
            }
        }
    }
}
