package com.ko.user_database

import androidx.room.*


@Dao
internal interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(user: User): Long

    @Delete
    fun deleteUser(user: User)

    @Transaction
    @Query("SELECT * FROM users_table")
    fun getAllUsers(): List<User>
}
