package com.ko.user_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
internal abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}
