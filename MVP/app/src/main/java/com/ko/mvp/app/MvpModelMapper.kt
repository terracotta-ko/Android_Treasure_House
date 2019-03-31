package com.ko.mvp.app

import com.ko.mvp.core.MvpDomain

interface MvpModelMapper {

    fun toModel(domains: List<MvpDomain>): List<MvpModel>
}

class MvpModelMapperDefault: MvpModelMapper {

    override fun toModel(domains: List<MvpDomain>): List<MvpModel> {
        return domains.map {
            MvpModel(
                it.userId.toString(),
                it.userName,
                it.isVerified.toString()
            )
        }
    }
}
