package com.ko.mvp.data

import com.ko.mvp.base.MvpContract
import com.ko.mvp.core.MvpDomain
import com.ko.user_database.UserLocalDataSource

internal class MvpRepository(
    private val userLocalDataSource: UserLocalDataSource,
    private val domainMapper: MvpDomainMapper
) : MvpContract.Repository {

    override suspend fun fetch(): MvpDomain {
        val users = userLocalDataSource.getAllUsers()
        return MvpDomain(users.map { domainMapper.toDomain(it) })
    }
}
