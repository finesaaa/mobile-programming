package com.finesaaa.simplepointofsales.model

import android.database.Cursor

abstract class Model {
  abstract fun getTableName(): String
  abstract fun getPrimaryKeyName(): String
  abstract fun setAllDataByCursor(cursor: Cursor)
  abstract fun toMap(): Map<String, Any>
  abstract fun getTableAttributes(): String

  fun getColumnIndex(cursor: Cursor, key: String): Int {
    return if(cursor.getColumnIndex(key) >= 0) {
      cursor.getColumnIndex(key)
    } else {
      0
    }
  }

}