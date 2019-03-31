package com.ko.common.coroutines

import android.os.AsyncTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

interface CoroutinesDispatcher {

    val IODispatcher: CoroutineContext

    val UIDispatcher: CoroutineContext
}

class CoroutinesDispatcherDefault: CoroutinesDispatcher {

    override val IODispatcher: CoroutineContext
        get() = AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    override val UIDispatcher: CoroutineContext
        get() = Dispatchers.Main
}


@ExperimentalCoroutinesApi
class CoroutinesDispatcherUnconfined: CoroutinesDispatcher {

    override val IODispatcher: CoroutineContext
        get() = Dispatchers.Unconfined
    override val UIDispatcher: CoroutineContext
        get() = Dispatchers.Unconfined
}
