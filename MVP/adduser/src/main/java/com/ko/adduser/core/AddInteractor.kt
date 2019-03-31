package com.ko.adduser.core

import com.ko.adduser.data.AddEntityMapper

class AddInteractor(
    private val repository: AddContract.Repository,
    private val entityMapper: AddEntityMapper
) : AddContract.Interactor {

    override suspend fun saveUser(domain: AddDomain) =
        repository.saveUser(entityMapper.toEntity(domain))
}
