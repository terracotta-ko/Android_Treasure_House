package com.ko.user_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val TABLE_NAME = "users_table"

private const val COLUMN_USER_ID = "column_user_id"
private const val COLUMN_USER_NAME = "column_user_name"
private const val COLUMN_USER_IS_VERIFIED = "column_user_is_verified"

@Entity(tableName = TABLE_NAME)
internal data class User(

    @PrimaryKey
    @ColumnInfo(name = COLUMN_USER_ID)
    val userId: Long,

    @ColumnInfo(name = COLUMN_USER_NAME)
    val userName: String,

    @ColumnInfo(name = COLUMN_USER_IS_VERIFIED)
    val isVerified: Boolean
)
