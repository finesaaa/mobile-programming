package com.finesaaa.contactapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.finesaaa.contactapp.databinding.FragmentContactListBinding
import com.finesaaa.contactapp.db.sqlite.ContactDatabase
import com.finesaaa.contactapp.dialog.AddContactDialog
import com.finesaaa.contactapp.model.ContactModel

class ContactListFragment: Fragment() {

  private var binding: FragmentContactListBinding? = null
  private lateinit var adapter: ContactListAdapter
  private lateinit var database: ContactDatabase

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentContactListBinding.inflate(inflater, container, false)
    database = ContactDatabase(context, arrayListOf(ContactModel()))

    setupButtons()
    setupList()
    return binding?.root
  }

  override fun onDestroy() {
    super.onDestroy()

    if (binding != null){
      binding = null
    }
  }

  private fun setupButtons() {
    binding?.fragmentClFabAdd?.setOnClickListener {
      AddContactDialog { data ->
        if (data == null) {
          showToast("Isi data dengan benar!")
        } else {
          adapter.addItem(data)
          database.insert(data)
        }
      }.show(parentFragmentManager, null)
    }
  }

  private fun setupList() {
    adapter = ContactListAdapter {
      findNavController().navigate(
        ContactListFragmentDirections.actionNavContactListToContactDetailFragment(it)
      )
    }
    binding?.fragmentClRvContact?.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(context)
      adapter = this@ContactListFragment.adapter
    }

    val contactList = database.read(ContactModel(), null)
    if (contactList.size >  0) {
      adapter.updateAllData(contactList)
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}