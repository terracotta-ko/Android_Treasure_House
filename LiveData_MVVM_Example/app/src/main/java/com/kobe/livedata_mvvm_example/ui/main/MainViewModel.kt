package com.kobe.livedata_mvvm_example.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
