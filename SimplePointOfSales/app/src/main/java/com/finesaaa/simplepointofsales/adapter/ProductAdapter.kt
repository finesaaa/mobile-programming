package com.finesaaa.simplepointofsales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.simplepointofsales.databinding.ItemTransactionBinding
import com.finesaaa.simplepointofsales.model.Product

class ProductAdapter(): RecyclerView.Adapter<ProductViewHolder>() {
  private val list: ArrayList<Product> = arrayListOf()

  override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductViewHolder {
    return ProductViewHolder(
      ItemTransactionBinding.inflate(
        LayoutInflater.from(viewGroup.context),
        viewGroup,
        false
      ).root)
  }

  override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    holder.bindItem(list[position])
  }

  override fun getItemCount(): Int {
    return list.size
  }

  fun clearAllData() {
    list.clear()
  }

  fun getAllData(): ArrayList<Product> {
    return list
  }

  fun addData(item: Product) {
    list.add(item)
  }

  fun updateAllData(datas: ArrayList<Product>) {
    if (list.size > 0) {
      list.clear()
    }

    list.addAll(datas)
  }
}