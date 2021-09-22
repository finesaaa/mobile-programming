package com.finesaaa.simplepointofsales.model

import java.io.Serializable

data class Invoice(
  var namaPembeli: String? = null,
  var namaBarang: String? = null,
  var jmlBarang: Double = 0.0,
  var harga: Double = 0.0,
  var jmlUang: Double = 0.0,
) {
  fun getReturn(): Double {
    return jmlUang - (jmlBarang * harga)
  }

  fun getTotal(): Double {
    return jmlBarang * harga
  }

  fun getReturnStatus(): String {
    return if (getReturn() < 0.0) {
      "Keterangan : Uang bayar kurang Rp. -${getReturn()}"
    } else if (getReturn() > 0.0) {
      "Keterangan : Lunas dengan kembalian Rp. ${getReturn()}"
    } else {
      "Lunas"
    }
  }

  fun getBonusStatus(): String {
    return if (getTotal() >= 200000) {
      "HardDisk 1TB"
    } else if (getTotal() >= 50000) {
      "Keyboard Gaming"
    } else if (getTotal() >= 40000) {
      "Mouse Gaming"
    } else {
      "Tidak ada bonus!"
    }
  }
}

