package com.ko.adduser.data

import com.ko.adduser.core.AddContract
import com.ko.user_database.UserDao
import com.ko.user_database.UserEntity

class AddRepository(private val userDao: UserDao): AddContract.Repository {

    override suspend fun saveUser(entity: UserEntity) {
        userDao.add(entity)
    }
}
