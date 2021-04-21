package com.ko.common.coroutines

import android.os.AsyncTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

interface CoroutinesDispatcher {

    val threadIO: CoroutineContext

    val threadUI: CoroutineContext
}

class CoroutinesDispatcherDefault: CoroutinesDispatcher {

    override val threadIO: CoroutineContext
        get() = Dispatchers.IO

    override val threadUI: CoroutineContext
        get() = Dispatchers.Main
}


@ExperimentalCoroutinesApi
class CoroutinesDispatcherUnconfined: CoroutinesDispatcher {

    override val threadIO: CoroutineContext
        get() = Dispatchers.Unconfined
    override val threadUI: CoroutineContext
        get() = Dispatchers.Unconfined
}
