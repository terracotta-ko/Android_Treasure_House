package com.ko.mvp.base

import com.ko.common.coroutines.CoroutinesDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class MvpPresenter(
    private val interactor: MvpContract.Interactor,
    private val modelMapper: MvpModelMapper,
    private val dispatcher: CoroutinesDispatcher
) : MvpContract.Presenter, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatcher.threadUI + job

    private var view: MvpContract.View? = null

    override fun bindView(view: MvpContract.View) {
        this.view = view
    }

    override fun unbindView() {
        this.view = null
        job.cancel()
    }

    override fun onViewCreated() {
        launch(dispatcher.threadUI) {
            val model = withContext(dispatcher.threadIO) {
                modelMapper.toModel(interactor.fetch())
            }
            view?.updateView(model.users)
        }
    }

    override fun onAddButtonClick() {
        view?.gotoAddActivity()
    }

    override fun onAddSucceeded() {
        launch(dispatcher.threadUI) {
            val model = withContext(dispatcher.threadIO) {
                modelMapper.toModel(interactor.fetch())
            }
            view?.updateView(model.users)
        }
    }
}
