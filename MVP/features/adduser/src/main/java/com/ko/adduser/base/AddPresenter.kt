package com.ko.adduser.base

import com.ko.adduser.R
import com.ko.adduser.core.AddDomainMapper
import com.ko.common.coroutines.CoroutinesDispatcher
import com.ko.common.utils.CommonStringResourceUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

internal class AddPresenter(
    private val interactor: AddContract.Interactor,
    private val dispatcher: CoroutinesDispatcher,
    private val domainMapper: AddDomainMapper,
    private val stringRes: CommonStringResourceUtils
): AddContract.Presenter, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatcher.threadUI + job

    private var view: AddContract.View? = null

    override fun bindView(view: AddContract.View) {
        this.view = view
    }

    override fun unbindView() {
        view = null
        job.cancel()
    }

    override fun saveUser(userId: String?, userName: String?) {
        launch(dispatcher.threadUI) {
            val result = withContext(dispatcher.threadIO) {
                val domain = domainMapper.toDomain(userId, userName)
                domain?.let { interactor.saveUser(it) } ?: false
            }

            if (result) {
                view?.closeWithResult()
            } else {
                view?.showError(stringRes.getString(R.string.error_unknown))
            }
        }
    }
}
