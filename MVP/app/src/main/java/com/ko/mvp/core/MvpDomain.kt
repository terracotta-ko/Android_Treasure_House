package com.ko.mvp.core

internal data class MvpDomain(
    val users: List<MvpUserDomain>
)

internal data class MvpUserDomain(
    val userId: Long,
    val userName: String,
    val isVerified: Boolean
)
