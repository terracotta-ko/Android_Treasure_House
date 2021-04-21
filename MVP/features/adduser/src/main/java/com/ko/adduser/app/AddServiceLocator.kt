package com.ko.adduser.app

import android.content.Context
import com.ko.adduser.base.AddContract
import com.ko.adduser.base.AddPresenter
import com.ko.adduser.core.*
import com.ko.adduser.data.AddEntityMapper
import com.ko.adduser.data.AddEntityMapperDefault
import com.ko.adduser.data.AddRepository
import com.ko.common.coroutines.CoroutinesDispatcher
import com.ko.common.coroutines.CoroutinesDispatcherDefault
import com.ko.common.utils.CommonStringResourceUtils
import com.ko.user_database.UserLocalDataSource
import com.ko.user_database.UserLocalDataSourceProvider

internal class AddServiceLocator(private val context: Context) {

    fun getPresenter(): AddContract.Presenter =
        AddPresenter(getInteractor(), getDispatcher(), getDomainMapper(), getStringRes())

    private fun getInteractor(): AddContract.Interactor =
        AddInteractor(getRepository(), getEntityMapper())

    private fun getRepository(): AddContract.Repository =
        AddRepository(getUserLocalDataSource())

    private fun getUserLocalDataSource(): UserLocalDataSource =
        UserLocalDataSourceProvider.getInstance(context).localDataSource

    private fun getEntityMapper(): AddEntityMapper =
        AddEntityMapperDefault()

    private fun getDispatcher(): CoroutinesDispatcher =
        CoroutinesDispatcherDefault()

    private fun getDomainMapper(): AddDomainMapper =
        AddDomainMapperDefault()

    private fun getStringRes() = CommonStringResourceUtils(context)
}
