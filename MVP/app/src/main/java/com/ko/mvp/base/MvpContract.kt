package com.ko.mvp.base

import com.ko.mvp.core.MvpDomain

internal interface MvpContract {

    interface View {

        fun updateView(models: List<MvpUserModel>)

        fun gotoAddActivity()
    }

    interface Presenter {

        fun bindView(view: View)

        fun unbindView()

        fun onViewCreated()

        fun onAddButtonClick()

        fun onAddSucceeded()
    }

    interface Interactor {

        suspend fun fetch(): MvpDomain
    }

    interface Repository {

        suspend fun fetch(): MvpDomain
    }
}
