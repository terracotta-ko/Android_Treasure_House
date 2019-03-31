package com.ko.adduser.core

import kotlin.random.Random

interface AddDomainMapper {

    fun toDomain(userId: String, userName: String): AddDomain
}

class AddDomainMapperDefault: AddDomainMapper {

    override fun toDomain(userId: String, userName: String): AddDomain {
        return AddDomain(userId.toLong(), userName, Random.nextBoolean())
    }
}
