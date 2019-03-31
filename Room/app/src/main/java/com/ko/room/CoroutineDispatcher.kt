package com.ko.room

import android.os.AsyncTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

interface CoroutineDispatcher {

    val IODispatcher: CoroutineContext

    val UIDispatcher: CoroutineContext
}

class CoroutineDispatcherDefault: CoroutineDispatcher {

    override val IODispatcher: CoroutineContext
        get() = AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    override val UIDispatcher: CoroutineContext
        get() = Dispatchers.Main
}


@ExperimentalCoroutinesApi
class CoroutineDispatcherUnconfined: CoroutineDispatcher {

    override val IODispatcher: CoroutineContext
        get() = Unconfined
    override val UIDispatcher: CoroutineContext
        get() = Unconfined
}
