package com.finesaaa.contactapp.dialog

import com.finesaaa.contactapp.model.ContactModel

data class AddContactDialogParams(
  var data: ContactModel? = null,
  var onPositiveClick: (model: ContactModel?) -> Unit = {}
)
