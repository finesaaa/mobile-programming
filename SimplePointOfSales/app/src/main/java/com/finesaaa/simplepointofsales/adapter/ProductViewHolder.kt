package com.finesaaa.simplepointofsales.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.simplepointofsales.databinding.ItemTransactionBinding
import com.finesaaa.simplepointofsales.model.Invoice
import com.finesaaa.simplepointofsales.model.Product

class ProductViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
  fun bindItem(item: Product) {
      val binding =  ItemTransactionBinding.bind(v)
      binding.tvNamaBarang.text = item.nama
      binding.tvJmlBarang.text = item.jumlah.toString()
      binding.tvHarga.text = "Rp " + item.harga.toString()
  }
}