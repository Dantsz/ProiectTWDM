package com.example.twdm_app.ui.networkscan

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twdm_app.databinding.FragmentWifiscanBinding

class WiFiScanFragment : Fragment() {

    private var _binding: FragmentWifiscanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess(context)
            } else {
                scanFailure(context)
            }
        }
    }
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wifiManager: WifiManager = this.context?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.startScan())
        {
            Log.e("WIFI", "FAILED TO START SCAN OF WIFI APs")
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        this.activity?.registerReceiver(wifiScanReceiver, intentFilter)
        if (wifiManager.wifiState == WifiManager.WIFI_STATE_ENABLED) {
            val results = wifiManager.scanResults
            Log.i("WIFI", results.toString())
        }
    }

    override fun onDestroy() {
        this.activity?.unregisterReceiver(wifiScanReceiver)
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[WiFiScanViewModel::class.java]

        _binding = FragmentWifiscanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        dashboardViewModel.scanResults.observe(viewLifecycleOwner) {
            val data = ArrayList<NetworkItemViewModel>()
            for (i in it)
            {
                data.add(NetworkItemViewModel(i.SSID))
            }
            val adapter = NetworkItemAdapter(data)
            binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
            binding.recyclerView.adapter = adapter
        }

        return root
    }

    @SuppressLint("MissingPermission")
    private fun scanSuccess(context: Context) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dashboardViewModel =
            ViewModelProvider(this)[WiFiScanViewModel::class.java]
        val results = wifiManager.scanResults
        dashboardViewModel.onScanUpdate(results)

        Log.i("WIFI", results.toString())
    }

    @SuppressLint("MissingPermission")
    private fun scanFailure(context: Context) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val results = wifiManager.scanResults
        Log.i("WIFI", results.toString())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}