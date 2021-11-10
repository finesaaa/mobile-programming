package com.finesaaa.jsonparserapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.jsonparserapp.databinding.ItemCasesBinding
import com.finesaaa.jsonparserapp.listener.CaseListener
import com.finesaaa.jsonparserapp.model.CaseModel
import com.squareup.picasso.Picasso

class CasesListViewHolder(private val v: View, private val listener: CaseListener) : RecyclerView.ViewHolder(v) {
  fun bind(item: CaseModel) {
    val binding = ItemCasesBinding.bind(v)
    binding.itemCTvName.text = item.country
    binding.itemCTvNum.text = "Cases :${item.cases}"
    Picasso.get()
      .load(item.countryInfo.flag)
      .resize(54, 54)
      .centerCrop()
      .placeholder(R.drawable.ic_baseline_map_24)
      .error(R.drawable.ic_baseline_map_24)
      .into(binding.itemCIv)

    binding.itemCRl.setOnClickListener { listener.onItemClick(item) }
  }
}
