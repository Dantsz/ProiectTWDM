package com.example.twdm_app.ui.networkscan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twdm_app.R

class NetworkItemAdapter(private val dataList: ArrayList<NetworkItemViewModel>):
    RecyclerView.Adapter<NetworkItemAdapter.ViewHolderClass>() {
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.network_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.network_item, parent, false)

        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {
      return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val itemViewModel = dataList[position]
        holder.nameText.text = itemViewModel.name
    }
}