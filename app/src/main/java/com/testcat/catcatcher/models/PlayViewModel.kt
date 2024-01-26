package com.testcat.catcatcher.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayViewModel : ViewModel() {
    val catxvalue = MutableLiveData<Float>()
    val catyvalue = MutableLiveData<Float>()
    val bedyvalue = MutableLiveData<Float>()
    val bedxvalue = MutableLiveData<Float>(300f)
    val changers = MutableLiveData<String>("true")
    val speed = MutableLiveData<Int>(10)
    val count = MutableLiveData<Int>(0)
    val pause = MutableLiveData<String>("false")
}