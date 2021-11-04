package com.finesaaa.contactapp.model

import android.database.Cursor
import com.finesaaa.contactapp.model.Model
import java.io.Serializable

data class ContactModel(
  var id_kontak: Long = 0L,
  var nama: String = "",
  var telepon: String = ""
): Model(), Serializable {
  override fun getTableName(): String {
    return "contact"
  }

  override fun getPrimaryKeyName(): String {
    return "id_kontak"
  }

  override fun setAllDataByCursor(cursor: Cursor): ContactModel {
    return ContactModel(
      id_kontak = cursor.getLong(getColumnIndex(cursor, "id_kontak")),
      nama = cursor.getString(getColumnIndex(cursor, "nama")),
      telepon = cursor.getString(getColumnIndex(cursor, "telepon"))
    )
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "nama" to nama,
      "telepon" to telepon
    )
  }

  override fun getTableAttributes(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
        "nama TEXT, telepon TEXT)"
  }

}
