package com.ko.mvp.core

import com.ko.mvp.app.MvpModel
import com.ko.user_database.UserEntity

interface MvpContract {

    interface View {

        fun update(models: List<MvpModel>)

        fun gotoAddActivity()
    }

    interface Presenter {

        fun bindView(view: View)

        fun unbindView()

        fun fetch()

        fun onAddButtonClick()
    }

    interface Interactor {

        suspend fun fetch(): List<MvpDomain>
    }

    interface Repository {

        suspend fun fetch(): List<UserEntity>
    }
}
