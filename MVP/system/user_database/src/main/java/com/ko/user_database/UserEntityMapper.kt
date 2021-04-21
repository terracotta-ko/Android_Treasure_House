package com.ko.user_database

internal interface UserEntityMapper {

    fun toEntity(user: User): UserEntity
}

internal class UserEntityMapperDefault: UserEntityMapper {

    override fun toEntity(user: User): UserEntity {
        return UserEntity(user.userId, user.userName, user.isVerified)
    }
}
