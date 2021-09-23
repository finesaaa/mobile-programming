package com.finesaaa.simplepointofsales.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.simplepointofsales.databinding.ItemTransactionBinding
import com.finesaaa.simplepointofsales.model.Invoice

class InvoiceViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
  fun bindItem(item: Invoice) {
      val binding =  ItemTransactionBinding.bind(v)
      binding.tvNamaBarang.text = item.namaBarang
      binding.tvJmlBarang.text = item.jmlBarang.toString()
      binding.tvHarga.text = "Rp " + item.harga.toString()
  }
}