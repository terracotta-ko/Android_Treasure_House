package com.ko.adduser.app

import android.content.Context
import com.ko.adduser.core.*
import com.ko.adduser.data.AddEntityMapper
import com.ko.adduser.data.AddEntityMapperDefault
import com.ko.adduser.data.AddRepository
import com.ko.common.coroutines.CoroutinesDispatcher
import com.ko.common.coroutines.CoroutinesDispatcherDefault
import com.ko.user_database.UserDao
import com.ko.user_database.UserDatabaseProvider

class AddServiceLocator(private val context: Context) {

    fun getPresenter(): AddContract.Presenter =
        AddPresenter(getInteractor(), getDispatcher(), getDomainMapper())

    private fun getInteractor(): AddContract.Interactor =
        AddInteractor(getRepository(), getEntityMapper())

    private fun getRepository(): AddContract.Repository =
        AddRepository(getUserDao())

    private fun getUserDao(): UserDao =
        UserDatabaseProvider.getUserDao(context)

    private fun getEntityMapper(): AddEntityMapper =
        AddEntityMapperDefault()

    private fun getDispatcher(): CoroutinesDispatcher =
        CoroutinesDispatcherDefault()

    private fun getDomainMapper(): AddDomainMapper =
        AddDomainMapperDefault()
}
