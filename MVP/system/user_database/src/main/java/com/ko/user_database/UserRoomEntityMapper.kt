package com.ko.user_database

internal interface UserRoomEntityMapper {

    fun toUser(entity: UserEntity): User
}

internal class UserRoomEntityMapperDefault: UserRoomEntityMapper {

    override fun toUser(entity: UserEntity): User {
        return User(entity.id, entity.name, entity.isVerified)
    }
}
