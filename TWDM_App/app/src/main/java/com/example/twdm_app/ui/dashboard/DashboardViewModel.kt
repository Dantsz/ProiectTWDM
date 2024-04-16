package com.example.twdm_app.ui.dashboard

import android.net.wifi.ScanResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _scanResults = MutableLiveData<List<ScanResult>>().apply {
        value = ArrayList()
    }
    val scanResults: LiveData<List<ScanResult>> = _scanResults

    fun onScanUpdate(results: MutableList<ScanResult>)
    {
        _scanResults.value = results
    }



}