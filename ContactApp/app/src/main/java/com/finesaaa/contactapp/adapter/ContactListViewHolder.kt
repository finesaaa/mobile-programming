package com.finesaaa.contactapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.contactapp.databinding.ItemContactBinding

class ContactListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
  fun bind(item: ContactModel) {
    val binding = ItemContactBinding.bind(v)

    binding.itemCTvName.text = item.nama
    binding.itemCTvNum.text = item.telepon
  }
}
