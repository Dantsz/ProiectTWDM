package com.example.twdm_app.ui.networkinterfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InterfacesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Wi-fi data not available"
    }
    val text: LiveData<String> = _text

    fun onWifiUpdate(info: String)
    {
        _text.value = info;
    }

}