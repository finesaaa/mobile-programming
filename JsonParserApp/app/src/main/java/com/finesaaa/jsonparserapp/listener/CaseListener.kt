package com.finesaaa.jsonparserapp.listener

import com.finesaaa.jsonparserapp.model.CaseModel

interface CaseListener {
  fun onItemClick(model: CaseModel)
}