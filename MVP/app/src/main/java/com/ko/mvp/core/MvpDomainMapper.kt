package com.ko.mvp.core

import com.ko.user_database.UserEntity

interface MvpDomainMapper {

    fun toDomain(entities: List<UserEntity>): List<MvpDomain>
}

class MvpDomainMapperDefault: MvpDomainMapper {

    override fun toDomain(entities: List<UserEntity>): List<MvpDomain> {
        return entities.map {
            MvpDomain(
                it.userId,
                it.userName,
                it.isVerified
            )
        }
    }
}
