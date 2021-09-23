package com.finesaaa.simplepointofsales.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.simplepointofsales.R
import com.finesaaa.simplepointofsales.databinding.ItemTransactionBinding
import com.finesaaa.simplepointofsales.model.Invoice

class InvoiceAdapter(): RecyclerView.Adapter<InvoiceViewHolder>() {
  private val invoiceList: ArrayList<Invoice> = arrayListOf()

  override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): InvoiceViewHolder {
    return InvoiceViewHolder(
      ItemTransactionBinding.inflate(
        LayoutInflater.from(viewGroup.context),
        viewGroup,
        false
      ).root)
  }

  override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
    val invoice = invoiceList[position]
    holder.bindItem(invoice)
  }

  override fun getItemCount(): Int {
    return invoiceList.size
  }

  fun clearAllData() {
    invoiceList.clear()
  }

  fun getAllData(): ArrayList<Invoice> {
    return invoiceList
  }

  fun addData(item: Invoice) {
    invoiceList.add(item)
  }
}