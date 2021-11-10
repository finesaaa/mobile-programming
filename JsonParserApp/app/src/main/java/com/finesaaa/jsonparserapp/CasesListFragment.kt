package com.finesaaa.jsonparserapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.finesaaa.jsonparserapp.databinding.FragmentCasesListBinding
import com.finesaaa.jsonparserapp.listener.CaseListener
import com.finesaaa.jsonparserapp.model.CaseModel
import com.finesaaa.jsonparserapp.service.CaseService
import com.finesaaa.jsonparserapp.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CasesListFragment: Fragment() {

  private var binding: FragmentCasesListBinding? = null
  private lateinit var adapter: CasesListAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCasesListBinding.inflate(inflater, container, false)

    setupList()

    return binding?.root
  }

  override fun onDestroy() {
    super.onDestroy()

    if (binding != null){
      binding = null
    }
  }

  private fun setupList() {
    val destinationService  = ServiceBuilder.buildService(CaseService::class.java)
    val requestCall =destinationService.getAffectedCountryList()

    requestCall.enqueue(object : Callback<List<CaseModel>> {
      override fun onResponse(call: Call<List<CaseModel>>, response: Response<List<CaseModel>>) {
        Log.d("Response", "onResponse: ${response.body()}")
        if (response.isSuccessful){
          val casesList  = (response.body()!!).toCollection(ArrayList())
          Log.d("Response", "countrylist size : ${casesList.size}")

          adapter = CasesListAdapter (
            object : CaseListener {
              override fun onItemClick(model: CaseModel) {
                findNavController().navigate(
                  CasesListFragmentDirections.actionNavCasesListToCasesDetailFragment(model)
                )
              }
            }
          )

          binding?.fragmentClRvContact?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@CasesListFragment.adapter
          }

          if (casesList.size >  0) {
            adapter.updateAllData(casesList)
          }

        }else{
          showToast("Something went wrong ${response.message()}")
        }
      }
      override fun onFailure(call: Call<List<CaseModel>>, t: Throwable) {
        showToast("Something went wrong $t")
      }
    })
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}