package com.finesaaa.simplepointofsales.db.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.finesaaa.simplepointofsales.model.Model

class OperationsDatabase(
  context: Context?,
  private val models: ArrayList<Model>,
  name: String? = "simple_pos_database",
  factory: SQLiteDatabase.CursorFactory? = null,
  version: Int = 1,
  errorHandler: DatabaseErrorHandler? = null
) : SQLiteOpenHelper(context, name, factory, version, errorHandler) {
  override fun onCreate(db: SQLiteDatabase?) {
    if (db != null) {
      models.forEach {
        createTable(db, it)
      }
    }
  }

  override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    if (db != null) {
      models.forEach {
        dropTable(db, it)
      }

      onCreate(db)
    }
  }

  private fun createTable(db: SQLiteDatabase, model: Model) {
    db.execSQL("CREATE TABLE ${model.getTableAttributes()}")
  }

  private fun dropTable(db: SQLiteDatabase, model: Model) {
    db.execSQL("DROP TABLE IF  EXISTS ${model.getTableName()}")
  }

  fun insert(model: Model): Long {
    val db = this.writableDatabase
    val contentValues = ContentValues()

    model.toMap().forEach { (key, value) ->
      when (value) {
        is String -> contentValues.put(key, value)
        is Long -> contentValues.put(key, value)
        is Int -> contentValues.put(key, value)
        is Double -> contentValues.put(key, value)
      }
    }

    return db.insert(model.getTableName(), null, contentValues)
  }

  fun <T: Model>read(id: Long?, model: T): ArrayList<T> {
    val db = this.readableDatabase
    val list = arrayListOf<T>()
    var query = ""
    val cursor: Cursor?

    if (id != null) {
      query = "SELECT * FROM ${model.getTableName()} " + "WHERE ${model.getPrimaryKeyName()}='$id'"

      cursor = db.rawQuery(query, null)
      if(cursor.count > 0) {
        if (cursor != null) {
          cursor.moveToFirst()
          model.setAllDataByCursor(cursor)
          list.add(model)
        }
      }
    } else {
      query = "SELECT * FROM ${model.getTableName()} "

      cursor = db.rawQuery(query, null)
      if(cursor.count > 0) {
        if (cursor != null) {
          do {
            cursor.moveToFirst()
            list.add(model)
          } while (cursor.moveToNext())
        }
      }
    }

    return list
  }

  fun update(model: Model, id: Long?): Int {
    val db = this.writableDatabase
    val contentValues = ContentValues()

    model.toMap().forEach { (key, value) ->
      when (value) {
        is String -> contentValues.put(key, value)
        is Long -> contentValues.put(key, value)
        is Int -> contentValues.put(key, value)
        is Double -> contentValues.put(key, value)
      }
    }

    return db.update(
      model.getTableName(),
      contentValues,
      "${model.getPrimaryKeyName()} = $id",
      null
    )
  }

  fun delete(id: Long, model: Model) {
    val db = this.writableDatabase
    db.delete(model.getTableName(), "${model.getPrimaryKeyName()} = $id", null)
  }
}