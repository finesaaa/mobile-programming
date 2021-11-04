package com.finesaaa.contactapp.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.finesaaa.contactapp.databinding.DialogAddContactBinding
import com.finesaaa.contactapp.model.ContactModel

class AddContactDialog(
  private val onPositiveClick: (contact: ContactModel?) -> Unit
) : DialogFragment() {

  var binding: DialogAddContactBinding? = null
  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DialogAddContactBinding.inflate(layoutInflater)

    alertDialog?.apply {
      setView(binding?.root)
      setPositiveButton("Tambahkan") { dialog, _ ->
        if (
          binding?.dialogAcTilName?.editText?.text.toString().isEmpty()
          || binding?.dialogAcTilNum?.editText?.text.toString().isEmpty()
        ) {
          onPositiveClick(null)
        } else {
          onPositiveClick(
            ContactModel(
              nama = binding?.dialogAcTilName?.editText?.text.toString(),
              telepon = binding?.dialogAcTilNum?.editText?.text.toString()
            )
          )
        }
      }
      setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
    }

    return alertDialog?.create()!!
  }

  override fun onDetach() {
    super.onDetach()

    if (binding != null) {
      binding = null
    }
    if (alertDialog != null) {
      alertDialog = null
    }
  }
}