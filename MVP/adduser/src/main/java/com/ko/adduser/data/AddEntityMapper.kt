package com.ko.adduser.data

import com.ko.adduser.core.AddDomain
import com.ko.user_database.UserEntity

interface AddEntityMapper {

    fun toEntity(domain: AddDomain): UserEntity
}

class AddEntityMapperDefault: AddEntityMapper {

    override fun toEntity(domain: AddDomain): UserEntity {
        return UserEntity(domain.userId, domain.userName, domain.isVerified)
    }
}
