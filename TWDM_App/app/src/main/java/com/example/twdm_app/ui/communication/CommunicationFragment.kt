package com.example.twdm_app.ui.communication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.twdm_app.databinding.FragmentCommunicationBinding
import kotlinx.coroutines.launch
import java.net.Socket
import java.util.Scanner

class CommunicationFragment : Fragment() {

    private var _binding: FragmentCommunicationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val communicationViewModel =
            ViewModelProvider(this)[CommunicationViewModel::class.java]

        _binding = FragmentCommunicationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        communicationViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        communicationViewModel.response.observe (viewLifecycleOwner) {
            binding.responseText.text = it
        }

        binding.button2.setOnClickListener {
            val network_thread = Thread {
                Log.d("WIFI", "Clicked send tcp")
                val address: String = binding.addrText.text.toString()
                val port: Int = binding.portText.text.toString().toInt()
                val connstr = "$address:$port";

                Log.d("WIFI", "Clicked send tcp, connecting to $connstr")
                val client = Socket(address, port)
                Log.d("WIFI", "Sending the message")
                val data = binding.messageText.text.toString() + '\n'
                client.getOutputStream().write(data.toByteArray())
                Log.d("WIFI", "MessageSent, waiting for response")
                val scanner = Scanner(client.getInputStream())
                val resp = scanner.nextLine()
                Log.d("WIFI", "Got response!$resp")
                communicationViewModel.onResponse(resp)
            }
            network_thread.start()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}