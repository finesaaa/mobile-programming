package com.finesaaa.contactapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.finesaaa.contactapp.databinding.FragmentContactListBinding
import com.finesaaa.contactapp.db.sqlite.ContactDatabase
import com.finesaaa.contactapp.dialog.AddContactDialog
import com.finesaaa.contactapp.dialog.AddContactDialogParams
import com.finesaaa.contactapp.dialog.ConfirmDialog
import com.finesaaa.contactapp.dialog.ConfirmDialogParams
import com.finesaaa.contactapp.listener.ContactListener
import com.finesaaa.contactapp.model.ContactModel
import java.util.*

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

    binding?.fragmentClTilSearch?.editText?.setOnEditorActionListener { view, i, _ ->
      return@setOnEditorActionListener if (id == EditorInfo.IME_ACTION_SEARCH) {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
          .hideSoftInputFromWindow(view.windowToken, 0)

        val query = binding?.fragmentClTilSearch?.editText?.text
        if (query != null) {
          val newList = arrayListOf<ContactModel>()
          adapter.getRawList().forEach { model ->
            if (model.nama.lowercase(Locale.getDefault())
                .contains(query) || model.telepon.lowercase(Locale.getDefault()).contains(query)
            ) {
              newList.add(model)
            }
          }
          adapter.updateAllData(newList)
        }
        true
      } else {
        false
      }
    }
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
      AddContactDialog (
        AddContactDialogParams (
          onPositiveClick = { data ->
            if (data == null) {
              showToast("Isi data dengan benar!")
            } else {
              adapter.addItem(data)
              database.insert(data)
            }
          }
        )
      ).show(parentFragmentManager, null)
    }
  }

  private fun setupList() {
    adapter = ContactListAdapter (
      object : ContactListener {
        override fun onItemClick(model: ContactModel) {
          findNavController().navigate(
            ContactListFragmentDirections.actionNavContactListToContactDetailFragment(model)
          )
        }

        override fun onItemEditClick(model: ContactModel) {
          AddContactDialog(
            AddContactDialogParams(
              data = model,
              onPositiveClick = { data ->
                if (data == null) {
                  showToast("Isi data dengan benar!")
                } else {
                  adapter.deleteItem(data)
                  database.delete(model, null, "telepon = ?", arrayOf(model.telepon))

                  adapter.addItem(data)
                  database.insert(data)
                }
              }
            )
          ).show(parentFragmentManager, null)
        }

        override fun onItemDeleteClick(model: ContactModel) {
          ConfirmDialog(
            ConfirmDialogParams(
              title = "Konfirmasi Hapus Kontak",
              message = "Apakah Anda yakin ingin menghapus kontak ini?",
              onPositiveClick = {
                adapter.deleteItem(model)
                database.delete(model, null, "telepon = ?", arrayOf(model.telepon))
              }
            )
          ).show(parentFragmentManager, null)
        }

      }
    )

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