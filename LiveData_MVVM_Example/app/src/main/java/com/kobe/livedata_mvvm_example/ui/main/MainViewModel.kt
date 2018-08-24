package com.kobe.livedata_mvvm_example.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var number: Int = 1
    private val _message = MutableLiveData<String>()

    //>> this is the public immutable LiveData
    //>> override the getter here
    val message: LiveData<String>
        get() = _message

    init {
        _message.value = "This is the ViewModel"
    }

    fun shuffleMessage() {
        _message.value = "Shuffle " + number++
    }
}
