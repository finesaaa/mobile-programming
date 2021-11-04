package com.finesaaa.simplepointofsales.model

import android.database.Cursor

data class Customer (
  var idPelanggan: Long = 0L,
  var nama: String = ""
): Model() {
  override fun getTableName(): String {
    return "pelanggan"
  }

  override fun getPrimaryKeyName(): String {
    return "idPelanggan"
  }

  override fun setAllDataByCursor(cursor: Cursor): Customer {
    return Customer(
      idPelanggan = cursor.getLong(getColumnIndex(cursor, "idPelanggan")),
      nama = cursor.getString(getColumnIndex(cursor, "nama"))
    )
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "nama" to nama
    )
  }

  override fun getTableAttributes(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
        "nama TEXT)"
  }

}

