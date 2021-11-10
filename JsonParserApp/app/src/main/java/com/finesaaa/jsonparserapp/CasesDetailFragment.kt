package com.finesaaa.jsonparserapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.finesaaa.jsonparserapp.databinding.FragmentCasesDetailBinding
import com.squareup.picasso.Picasso

class CasesDetailFragment: Fragment() {

  private var binding: FragmentCasesDetailBinding? = null
  private val args: CasesDetailFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCasesDetailBinding.inflate(inflater, container, false)

    if (args.cases != null) {
      binding?.fragmentCdTvName?.text = args.cases?.country
      binding?.fragmentCdTvNum?.text = "Cases :${args.cases?.cases}"
      binding?.fragmentCdTvTodayCase?.text = "Today Cases :${args.cases?.todayCases}"
      binding?.fragmentCdTvDeath?.text = "Death :${args.cases?.deaths}"
      binding?.fragmentCdTvRecovered?.text = "Recovered :${args.cases?.recovered}"
      Picasso.get()
        .load(args.cases?.countryInfo?.flag)
        .placeholder(R.drawable.ic_baseline_map_24)
        .error(R.drawable.ic_baseline_map_24)
        .into(binding?.fragmentCdIvFlag)
    }
    return binding?.root
  }
}