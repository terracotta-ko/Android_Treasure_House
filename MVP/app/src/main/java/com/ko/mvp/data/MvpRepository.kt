package com.ko.mvp.data

import com.ko.mvp.core.MvpContract
import com.ko.user_database.UserDao
import com.ko.user_database.UserEntity

class MvpRepository(private val userDao: UserDao): MvpContract.Repository {

    override suspend fun fetch(): List<UserEntity> =
        userDao.getAllUsers()
}
