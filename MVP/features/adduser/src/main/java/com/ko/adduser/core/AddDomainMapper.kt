package com.ko.adduser.core

import kotlin.random.Random

interface AddDomainMapper {

    fun toDomain(userId: String?, username: String?): AddDomain?
}

class AddDomainMapperDefault : AddDomainMapper {

    override fun toDomain(userId: String?, username: String?): AddDomain? {
        return when {
            userId == null || username == null -> null
            userId.isBlank() || username.isBlank() -> null
            else -> AddDomain(userId.toLong(), username, Random.nextBoolean())
        }
    }
}
