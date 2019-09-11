package com.ko.user_database

import androidx.room.*


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Transaction
    @Query("SELECT * FROM USERS")
    fun getAllUsers(): List<UserEntity>
}
