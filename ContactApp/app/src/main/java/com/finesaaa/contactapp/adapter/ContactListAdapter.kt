package com.finesaaa.contactapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.contactapp.databinding.ItemContactBinding

class ContactListAdapter : RecyclerView.Adapter<ContactListViewHolder>() {

  private val list = arrayListOf<ContactModel>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
    return ContactListViewHolder(
      ItemContactBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ).root
    )
  }

  override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
    holder.bind(list[position])
  }

  override fun getItemCount(): Int {
    return list.size
  }

  fun updateAllData(data: ArrayList<ContactModel>) {
    if (list.size > 0) {
      list.clear()
    }

    list.addAll(data)
  }

  fun addItem(item: ContactModel) {
    list.add(item)
  }

  fun clearAllData() {
    list.clear()
  }
}
