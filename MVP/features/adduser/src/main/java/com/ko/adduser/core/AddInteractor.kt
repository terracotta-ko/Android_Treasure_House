package com.ko.adduser.core

import com.ko.adduser.base.AddContract
import com.ko.adduser.data.AddEntityMapper

internal class AddInteractor(
    private val repository: AddContract.Repository,
    private val entityMapper: AddEntityMapper
) : AddContract.Interactor {

    override suspend fun saveUser(domain: AddDomain) =
        repository.saveUser(entityMapper.toEntity(domain))
}
