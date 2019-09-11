package com.ko.user_database

import android.content.Context
import androidx.room.Room

class UserDatabaseProvider {

    companion object {

        private const val USER_DATABASE = "user_database.db"

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getUserDao(context: Context): UserDao =
            getInstance(context).userDao()

        private fun getInstance(context: Context): UserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                USER_DATABASE
            )
                .build()
    }
}
