package com.finesaaa.contactapp.listener

import com.finesaaa.contactapp.model.ContactModel

interface ContactListener {
  fun onItemClick(model: ContactModel)
  fun onItemEditClick(model: ContactModel)
  fun onItemDeleteClick(model: ContactModel)
}