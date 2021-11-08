package com.finesaaa.contactapp.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.finesaaa.contactapp.databinding.DialogAddContactBinding
import com.finesaaa.contactapp.model.ContactModel

class ConfirmDialog(
  private val params: ConfirmDialogParams
) : DialogFragment() {

  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    alertDialog?.apply {
      setTitle(params.title)
      setMessage(params.message)
      setPositiveButton("Ya") { dialog, _ ->
        params.onPositiveClick()
        dialog.dismiss()
      }
      setNegativeButton("Batal") { dialog, _ ->
        params.onNegativeClick
        dialog.dismiss()
      }
    }

    return alertDialog?.create()!!
  }

  override fun onDetach() {
    super.onDetach()

    if (alertDialog != null) {
      alertDialog = null
    }
  }
}