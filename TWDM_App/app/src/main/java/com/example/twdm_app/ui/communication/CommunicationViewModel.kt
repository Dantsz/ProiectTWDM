package com.example.twdm_app.ui.communication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommunicationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text


    private val _response =  MutableLiveData<String>().apply {
        value = ""
    }

    val response: LiveData<String> = _response;

    public fun onResponse(resp: String) {
       _response.postValue(resp);
    }
}