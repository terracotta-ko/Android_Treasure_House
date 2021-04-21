package com.ko.mvp.base

internal data class MvpModel(
    val users: List<MvpUserModel>
)

internal data class MvpUserModel(
    val userId: String,
    val userName: String,
    val isVerified: String
)
