package com.finesaaa.simplepointofsales.model

data class Invoice(
  var namaBarang: String? = null,
  var jmlBarang: Double = 0.0,
  var harga: Double = 0.0,
) {

  fun getTotal(): Double {
    return jmlBarang * harga
  }
}

