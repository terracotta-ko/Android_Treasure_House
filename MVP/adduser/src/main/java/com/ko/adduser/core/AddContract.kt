package com.ko.adduser.core

import com.ko.user_database.UserEntity

interface AddContract {

    interface View {

        fun close()
    }

    interface Presenter {

        fun bindView(view: View)

        fun unbindView()

        fun saveUser(userId: String, userName: String)
    }

    interface Interactor {

        suspend fun saveUser(domain: AddDomain)
    }

    interface Repository {

        suspend fun saveUser(entity: UserEntity)
    }
}
