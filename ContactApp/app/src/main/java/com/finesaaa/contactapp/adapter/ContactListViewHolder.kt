package com.finesaaa.contactapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.contactapp.databinding.ItemContactBinding
import com.finesaaa.contactapp.model.ContactModel

class ContactListViewHolder(private val v: View, private val onItemClick: (ContactModel) -> Unit) : RecyclerView.ViewHolder(v) {
  fun bind(item: ContactModel) {
    val binding = ItemContactBinding.bind(v)

    binding.itemCTvName.text = item.nama
    binding.itemCTvNum.text = item.telepon

    binding.itemCRl.setOnClickListener {
      onItemClick(item)
    }
  }
}
