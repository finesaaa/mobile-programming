package com.finesaaa.simplepointofsales.model

import android.database.Cursor

data class Invoice(
  var idPembayaran: Long = 0L,
  var idPelanggan: Long = 0L,
  var uangBayar: Double = 0.0,
  var totalPembelian: Double = 0.0,
  var kembalian: Double = 0.0,
  var bonus: String = "",
  var keterangan: String = ""
): Model() {
  override fun getTableName(): String {
    return "pembayaran"
  }

  override fun getPrimaryKeyName(): String {
    return "idPembayaran"
  }

  override fun setAllDataByCursor(cursor: Cursor): Invoice {
    return Invoice (
          idPembayaran = cursor.getLong(getColumnIndex(cursor, "idPembayaran")),
          idPelanggan = cursor.getLong(getColumnIndex(cursor, "idPelanggan")),
          uangBayar = cursor.getDouble(getColumnIndex(cursor, "uangBayar")),
          totalPembelian = cursor.getDouble(getColumnIndex(cursor, "totalPembelian")),
          kembalian = cursor.getDouble(getColumnIndex(cursor, "kembalian")),
          bonus = cursor.getString(getColumnIndex(cursor, "bonus")),
          keterangan = cursor.getString(getColumnIndex(cursor, "keterangan"))
        )
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "idPelanggan" to idPelanggan,
      "uangBayar" to uangBayar,
      "totalPembelian" to totalPembelian,
      "kembalian" to kembalian,
      "bonus" to bonus,
      "keterangan" to keterangan
    )
  }

  override fun getTableAttributes(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
        "idPelanggan INTEGER, uangBayar REAL, totalPembelian REAL, kembalian REAL, bonus TEXT, keterangan TEXT)"
  }

}

