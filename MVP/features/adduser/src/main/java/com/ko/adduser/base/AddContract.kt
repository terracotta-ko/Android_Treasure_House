package com.ko.adduser.base

import com.ko.adduser.core.AddDomain
import com.ko.user_database.UserEntity

internal interface AddContract {

    interface View {

        fun showError(error: String)

        fun closeWithResult()
    }

    interface Presenter {

        fun bindView(view: View)

        fun unbindView()

        fun saveUser(userId: String?, userName: String?)
    }

    interface Interactor {

        suspend fun saveUser(domain: AddDomain): Boolean
    }

    interface Repository {

        suspend fun saveUser(entity: UserEntity): Boolean
    }
}
