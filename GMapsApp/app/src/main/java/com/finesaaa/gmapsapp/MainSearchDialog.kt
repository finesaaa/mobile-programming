package com.finesaaa.gmapsapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.finesaaa.gmapsapp.databinding.DialogMainSearchBinding

class MainSearchDialog(private val params: MainSearchDialogParams) : DialogFragment() {

  var binding: DialogMainSearchBinding? = null
  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DialogMainSearchBinding.inflate(layoutInflater)

    alertDialog?.apply {
      setTitle("Pilihan Pencarian Lokasi")
      setView(binding?.root)
      setPositiveButton("") { dialog, _ -> }
      setNegativeButton("") { dialog, _ -> }
    }

    binding?.dmainLatlong?.setOnClickListener {
      dismiss()
      params.onLatLongSearchClick()
    }
    binding?.dmainRealloc?.setOnClickListener {
      dismiss()
      params.onRealLocationClick()
    }
    binding?.dmainLastloc?.setOnClickListener {
      dismiss()
      params.onLastLocationClick()
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
