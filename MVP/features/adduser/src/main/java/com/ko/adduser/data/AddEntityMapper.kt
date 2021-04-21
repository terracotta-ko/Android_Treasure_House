package com.ko.adduser.data

import com.ko.adduser.core.AddDomain
import com.ko.user_database.UserEntity

internal interface AddEntityMapper {

    fun toEntity(domain: AddDomain): UserEntity
}

internal class AddEntityMapperDefault: AddEntityMapper {

    override fun toEntity(domain: AddDomain): UserEntity {
        return UserEntity(domain.userId, domain.userName, domain.isVerified)
    }
}
