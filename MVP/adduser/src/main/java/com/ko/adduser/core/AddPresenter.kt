package com.ko.adduser.core

import com.ko.common.coroutines.CoroutinesDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddPresenter(
    private val interactor: AddContract.Interactor,
    private val dispatcher: CoroutinesDispatcher,
    private val domainMapper: AddDomainMapper
): AddContract.Presenter, CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var view: AddContract.View? = null

    override fun bindView(view: AddContract.View) {
        this.view = view
        job = Job()
    }

    override fun unbindView() {
        job.cancel()
        view = null
    }

    override fun saveUser(userId: String, userName: String) {
        launch(dispatcher.IODispatcher) {
            interactor.saveUser(domainMapper.toDomain(userId, userName))
            launch(dispatcher.UIDispatcher) {
                view?.close()
            }
        }
    }
}
