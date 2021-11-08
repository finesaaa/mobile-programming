package com.finesaaa.contactapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.contactapp.databinding.ItemContactBinding
import com.finesaaa.contactapp.listener.ContactListener
import com.finesaaa.contactapp.model.ContactModel

class ContactListAdapter(private val listener: ContactListener) :
  RecyclerView.Adapter<ContactListViewHolder>() {

  private val list = arrayListOf<ContactModel>()
  private val rawList = arrayListOf<ContactModel>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
    return ContactListViewHolder(
      ItemContactBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ).root, listener
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
    if (rawList.size > 0) {
      rawList.clear()
    }

    rawList.addAll(data)
    list.addAll(data)
  }

  fun addItem(item: ContactModel) {
    list.add(item)
    rawList.add(item)
  }

  fun deleteItem(item: ContactModel) {
    list.remove(item)
    rawList.remove(item)
  }

  fun getRawList(): ArrayList<ContactModel> = rawList

  fun clearAllData() {
    list.clear()
    rawList.clear()
  }
}
