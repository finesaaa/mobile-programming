package com.finesaaa.simplepointofsales.model

import android.database.Cursor

data class Transaction(
  var idTransaksi: Long = 0L,
  var idPembayaran: Long = 0L,
  var idProduk: Long = 0L,
): Model() {
  override fun getTableName(): String {
    return "transaksi"
  }

  override fun getPrimaryKeyName(): String {
    return "idTransaksi"
  }

  override fun setAllDataByCursor(cursor: Cursor) {
    TODO("Not yet implemented")
  }

  override fun toMap(): Map<String, Any> {
    TODO("Not yet implemented")
  }

  override fun getTableAttributes(): String {
    TODO("Not yet implemented")
  }

}
