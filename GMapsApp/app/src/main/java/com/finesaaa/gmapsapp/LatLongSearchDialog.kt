package com.finesaaa.gmapsapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.finesaaa.gmapsapp.databinding.DialogSearchLatlongBinding

class LatLongSearchDialog(private val onClick: (Double, Double, Float) -> Unit) : DialogFragment() {

  var binding: DialogSearchLatlongBinding? = null
  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DialogSearchLatlongBinding.inflate(layoutInflater)

    alertDialog?.apply {
      setTitle("Cari Lat Long")
      setView(binding?.root)
      setPositiveButton("Temukan") { dialog, _ ->
        var isValid = true

        if (binding?.dlatlongTiLatitude?.editText?.text.isNullOrEmpty()) {
          isValid = false
        }
        if (binding?.dlatlongTiLongitude?.editText?.text.isNullOrEmpty()) {
          isValid = false
        }

        if (isValid) {
          if (binding?.dlatlongTiZoom?.editText?.text.isNullOrEmpty()) {
            onClick(
              binding?.dlatlongTiLatitude?.editText?.text.toString().toDouble(),
              binding?.dlatlongTiLongitude?.editText?.text.toString().toDouble(),
              8f
            )
          } else {
            onClick(
              binding?.dlatlongTiLatitude?.editText?.text.toString().toDouble(),
              binding?.dlatlongTiLongitude?.editText?.text.toString().toDouble(),
              binding?.dlatlongTiZoom?.editText?.text.toString().toFloat()
            )
          }
        } else {
          Toast.makeText(context, "Isi isian dengan benar", Toast.LENGTH_LONG).show()
        }

        dialog.dismiss()
      }
      setNegativeButton("Batal") { dialog, _ ->
        dialog.dismiss()
      }
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
