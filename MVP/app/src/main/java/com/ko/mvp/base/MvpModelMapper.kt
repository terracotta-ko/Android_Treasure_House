package com.ko.mvp.base

import com.ko.mvp.core.MvpDomain

internal interface MvpModelMapper {

    fun toModel(domain: MvpDomain): MvpModel
}

internal class MvpModelMapperDefault : MvpModelMapper {

    override fun toModel(domain: MvpDomain): MvpModel {
        val users = domain.users.map {
            MvpUserModel(
                it.userId.toString(),
                it.userName,
                it.isVerified.toString()
            )
        }
        return MvpModel(users)
    }
}
