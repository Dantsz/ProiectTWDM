package com.example.twdm_app.ui.home

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.twdm_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        textView.movementMethod = ScrollingMovementMethod()

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.button3.setOnClickListener {
            val wifiManager = this.context?.getSystemService(Context.WIFI_SERVICE) as WifiManager;
            val wifiInfo = wifiManager.connectionInfo;
            Log.i("WIFI", wifiInfo.toString());
            if (wifiInfo !== null)
            {
                homeViewModel.onWifiUpdate(wifiInfo.toString().replace(",","\n"))
            }
        }

        val wifiManager = this.context?.getSystemService(Context.WIFI_SERVICE) as WifiManager;
        val wifiInfo = wifiManager.connectionInfo;
        if (wifiInfo !== null)
        {
            homeViewModel.onWifiUpdate(wifiInfo.toString().replace(",","\n"))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}