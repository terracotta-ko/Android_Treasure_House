package com.ko.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

private const val TABLE_NAME = "users"

const val COLUMN_USER_ID = "column_user_id"
private const val COLUMN_USER_NAME = "column_user_name"
private const val COLUMN_USER_IS_VERIFIED = "column_user_IS_VERIFIED"

@Entity(tableName = TABLE_NAME)
data class UserEntity(

    @PrimaryKey
    @ColumnInfo(name = COLUMN_USER_ID)
    val userId: Long,

    @ColumnInfo(name = COLUMN_USER_NAME)
    val userName: String,

    @ColumnInfo(name = COLUMN_USER_IS_VERIFIED)
    val isVerified: Boolean
)
