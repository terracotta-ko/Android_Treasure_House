package com.ko.user_database

interface UserLocalDataSource {

    suspend fun addUser(user: UserEntity): Boolean

    suspend fun getAllUsers(): List<UserEntity>
}

internal class UserLocalDataSourceDefault(
    private val userDao: UserDao,
    private val userEntityMapper: UserEntityMapper,
    private val userRoomEntityMapper: UserRoomEntityMapper
): UserLocalDataSource {

    override suspend fun addUser(user: UserEntity): Boolean {
        val roomEntity = userRoomEntityMapper.toUser(user)
        return userDao.add(roomEntity) > 0
    }

    override suspend fun getAllUsers(): List<UserEntity> {
        val users = userDao.getAllUsers()
        return users.map { userEntityMapper.toEntity(it) }
    }
}