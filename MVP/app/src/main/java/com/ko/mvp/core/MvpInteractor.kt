package com.ko.mvp.core

import com.ko.mvp.base.MvpContract

internal class MvpInteractor(
    private val repository: MvpContract.Repository
): MvpContract.Interactor {

    override suspend fun fetch(): MvpDomain {
        //>> you can add more business logic here in the future
        return repository.fetch()
    }
}
