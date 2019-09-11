package com.ko.room.data

import androidx.room.Room
import android.content.Context

class UserDatabaseProvider {

    companion object {

        private const val USER_DATABASE = "user_database.db"

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase =
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
