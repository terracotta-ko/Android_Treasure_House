package com.ko.mvp.core

class MvpInteractor(
    private val repository: MvpContract.Repository,
    private val domainMapper: MvpDomainMapper
): MvpContract.Interactor {

    override suspend fun fetch(): List<MvpDomain> =
        domainMapper.toDomain(repository.fetch())
}
