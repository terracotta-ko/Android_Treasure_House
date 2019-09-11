package com.ko.room.data

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Transaction
    @Query("SELECT * FROM USERS")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM USERS WHERE $COLUMN_USER_ID = :userId")
    fun getUser(userId: Long): UserEntity
}
