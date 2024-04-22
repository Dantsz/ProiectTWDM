package com.example.twdm_app.ui.communication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.twdm_app.databinding.FragmentCommunicationBinding

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

       binding.button2.setOnClickListener {
           Log.d("Button", "Clicked")
       }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}