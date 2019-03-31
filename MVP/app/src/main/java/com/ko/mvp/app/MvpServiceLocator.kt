package com.ko.mvp.app

import android.content.Context
import com.ko.common.coroutines.CoroutinesDispatcher
import com.ko.common.coroutines.CoroutinesDispatcherDefault
import com.ko.common.navigation.AddActivityNavigator
import com.ko.mvp.core.*
import com.ko.mvp.data.MvpRepository
import com.ko.mvp.navigation.NavigationProviderDefault
import com.ko.user_database.UserDao
import com.ko.user_database.UserDatabaseProvider

class MvpServiceLocator(private val context: Context) {

    fun getPresenter(): MvpContract.Presenter =
            MvpPresenter(getInteractor(), getModelMapper(), getDispatcher())

    fun getAdapter(): MvpRecyclerViewAdapter =
        MvpRecyclerViewAdapter()

    fun getAddActivityNavigator(): AddActivityNavigator =
        NavigationProviderDefault().getAddActivityNavigator(context)

    private fun getInteractor(): MvpContract.Interactor =
            MvpInteractor(getRepository(), getDomainMapper())

    private fun getRepository(): MvpContract.Repository =
            MvpRepository(getMvpDao())

    private fun getMvpDao(): UserDao =
            UserDatabaseProvider.getUserDao(context)

    private fun getDomainMapper(): MvpDomainMapper =
            MvpDomainMapperDefault()

    private fun getModelMapper(): MvpModelMapper =
            MvpModelMapperDefault()

    private fun getDispatcher(): CoroutinesDispatcher =
            CoroutinesDispatcherDefault()
}
