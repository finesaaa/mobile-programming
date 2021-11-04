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

  override fun setAllDataByCursor(cursor: Cursor): Model {
    return Transaction(
      idTransaksi = cursor.getLong(getColumnIndex(cursor, "idTransaksi")),
      idPembayaran = cursor.getLong(getColumnIndex(cursor, "idPembayaran")),
      idProduk = cursor.getLong(getColumnIndex(cursor, "idProduk"))
    )
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "idTransaksi" to idTransaksi,
      "idProduk" to idProduk
    )
  }

  override fun getTableAttributes(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
        "idPembayaran INTEGER, idProduk INTEGER)"
  }

}
