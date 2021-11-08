package com.finesaaa.contactapp.dialog

data class ConfirmDialogParams(
  var title: String = "Konfirmasi",
  var message: String = "",
  var onPositiveClick: () -> Unit = {},
  var onNegativeClick: () -> Unit = {}
)
