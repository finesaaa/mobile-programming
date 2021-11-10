package com.finesaaa.jsonparserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finesaaa.jsonparserapp.databinding.ItemCasesBinding
import com.finesaaa.jsonparserapp.listener.CaseListener
import com.finesaaa.jsonparserapp.model.CaseModel

class CasesListAdapter(private val listener: CaseListener) :
  RecyclerView.Adapter<CasesListViewHolder>() {

  private val list = arrayListOf<CaseModel>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasesListViewHolder {
    return CasesListViewHolder(
      ItemCasesBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ).root, listener
    )
  }

  override fun onBindViewHolder(holder: CasesListViewHolder, position: Int) {
    holder.bind(list[position])
  }

  override fun getItemCount(): Int {
    return list.size
  }

  fun updateAllData(data: ArrayList<CaseModel>) {
    if (list.isNotEmpty()) {
      list.clear()
    }
    list.addAll(data)
  }
}
