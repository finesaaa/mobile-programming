package com.finesaaa.simplepointofsales.model

import android.database.Cursor

data class Product(
  var id: Long = 0L,
  var nama: String = "",
  var harga: Double = 0.0,
  var jumlah: Int = 0
): Model() {
  override fun getTableName(): String {
    return "produk"
  }

  override fun getPrimaryKeyName(): String {
    return "idProduk"
  }

  override fun setAllDataByCursor(cursor: Cursor) {
    id = cursor.getLong(getColumnIndex(cursor, "id"))
    nama = cursor.getString(getColumnIndex(cursor, "nama"))
    harga = cursor.getDouble(getColumnIndex(cursor, "harga"))
    jumlah = cursor.getInt(getColumnIndex(cursor, "jumlah"))
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "nama" to nama,
      "harga" to harga,
      "jumlah" to jumlah
    )
  }

  override fun getTableAttributes(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
        "nama TEXT, harga REAL, jumlah INTEGER)"
  }

  fun getTotalPrice(): Double {
    return jumlah * harga
  }
}
