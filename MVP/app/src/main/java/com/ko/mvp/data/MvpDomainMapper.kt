package com.ko.mvp.data

import com.ko.mvp.core.MvpUserDomain
import com.ko.user_database.UserEntity

internal interface MvpDomainMapper {

    fun toDomain(entity: UserEntity): MvpUserDomain
}

internal class MvpDomainMapperDefault : MvpDomainMapper {

    override fun toDomain(entity: UserEntity): MvpUserDomain {
        return MvpUserDomain(entity.id, entity.name, entity.isVerified)
    }
}
