package com.ko.mvp.core

import com.ko.common.coroutines.CoroutinesDispatcher
import com.ko.mvp.app.MvpModelMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MvpPresenter(
    private val interactor: MvpContract.Interactor,
    private val modelMapper: MvpModelMapper,
    private val dispatcher: CoroutinesDispatcher
): MvpContract.Presenter, CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var view: MvpContract.View? = null

    override fun bindView(view: MvpContract.View) {
        job = Job()
        this.view = view
    }

    override fun unbindView() {
        job.cancel()
        this.view = null
    }

    override fun fetch() {
        launch(dispatcher.IODispatcher) {
            val models = modelMapper.toModel(interactor.fetch())
            launch(dispatcher.UIDispatcher) {
                view?.update(models)
            }
        }
    }

    override fun onAddButtonClick() {
        view?.gotoAddActivity()
    }
}
