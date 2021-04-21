package com.ko.mvp.app

import android.content.Context
import com.ko.common.coroutines.CoroutinesDispatcher
import com.ko.common.coroutines.CoroutinesDispatcherDefault
import com.ko.common.navigation.AddActivityNavigator
import com.ko.mvp.base.MvpContract
import com.ko.mvp.base.MvpModelMapper
import com.ko.mvp.base.MvpModelMapperDefault
import com.ko.mvp.base.MvpPresenter
import com.ko.mvp.core.MvpInteractor
import com.ko.mvp.data.MvpDomainMapper
import com.ko.mvp.data.MvpDomainMapperDefault
import com.ko.mvp.data.MvpRepository
import com.ko.mvp.navigation.NavigationProviderDefault
import com.ko.user_database.UserLocalDataSource
import com.ko.user_database.UserLocalDataSourceProvider

internal class MvpServiceLocator(private val context: Context) {

    fun getPresenter(): MvpContract.Presenter =
        MvpPresenter(getInteractor(), getModelMapper(), getDispatcher())

    fun getAdapter(): MvpRecyclerViewAdapter =
        MvpRecyclerViewAdapter()

    fun getAddActivityNavigator(): AddActivityNavigator =
        NavigationProviderDefault().getAddActivityNavigator(context)

    private fun getInteractor(): MvpContract.Interactor =
        MvpInteractor(getRepository())

    private fun getRepository(): MvpContract.Repository =
        MvpRepository(getUserLocalDataSource(), getDomainMapper())

    private fun getUserLocalDataSource(): UserLocalDataSource =
        UserLocalDataSourceProvider.getInstance(context).localDataSource

    private fun getDomainMapper(): MvpDomainMapper =
        MvpDomainMapperDefault()

    private fun getModelMapper(): MvpModelMapper =
        MvpModelMapperDefault()

    private fun getDispatcher(): CoroutinesDispatcher =
        CoroutinesDispatcherDefault()
}
