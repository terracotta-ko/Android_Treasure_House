package com.ko.room

import com.ko.room.data.UserEntity

class UserMapper {

    fun toUser(userEntity: List<UserEntity>): List<User> =
        userEntity.map {
            User(it.userId, it.userName, it.isVerified)
        }
}
