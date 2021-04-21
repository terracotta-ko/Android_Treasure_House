package com.ko.adduser.data

import com.ko.adduser.base.AddContract
import com.ko.adduser.core.AddDomain
import com.ko.user_database.UserEntity
import com.ko.user_database.UserLocalDataSource


internal class AddRepository(
    private val userLocalDataSource: UserLocalDataSource
) : AddContract.Repository {

    override suspend fun saveUser(entity: UserEntity): Boolean =
        userLocalDataSource.addUser(entity)
}
