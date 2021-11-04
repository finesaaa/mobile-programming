package com.finesaaa.contactapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.finesaaa.contactapp.databinding.FragmentContactDetailBinding
import com.finesaaa.contactapp.model.ContactModel

class ContactDetailFragment: Fragment() {

  private var binding: FragmentContactDetailBinding? = null
  private val args: ContactDetailFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentContactDetailBinding.inflate(inflater, container, false)

    if (args.contact != null) {
      binding?.fragmentCdTvName?.text = args.contact?.nama
      binding?.fragmentCdTvPhone?.text = args.contact?.telepon
    }
    return binding?.root
  }
}