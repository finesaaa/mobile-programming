package com.finesaaa.simpledb

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.finesaaa.simpledb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var myDB: SQLiteDatabase
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    myDB = openOrCreateDatabase("db.sql", MODE_PRIVATE, null)
    myDB.execSQL("create table if not exists mhs(nrp TEXT, nama TEXT);")

    setupButtons()
  }

  override fun onStop() {
    myDB.close()
    super.onStop()
  }

  private fun setupButtons() {
    binding.btnSimpan.setOnClickListener {
      if (validateInput()) {
        val myData = ContentValues()
        myData.put("nrp", binding.etNRP.text.toString())
        myData.put("nama", binding.etNama.text.toString())
        myDB.insert("mhs", null, myData)
        showToast("Data Tersimpan")
      }
    }

    binding.btnAmbilData.setOnClickListener {
      val cur = myDB.rawQuery("select * from mhs where nrp='" +
          binding.etNRP.text.toString() + "'",null)

      if (cur.count > 0) {
        showToast("Data Ditemukan Sejumlah " + cur.count)
        cur.moveToFirst()
        val index = if (cur.getColumnIndex("nama") > 0) {
          cur.getColumnIndex("nama")
        } else {
          0
        }
        binding.etNama.setText(cur.getString(index))
      } else {
        showToast("Data Tidak Ditemukan")
      }
    }

    binding.btnUpdate.setOnClickListener {
      if (validateInput()) {
        val myData = ContentValues()
        myData.put("nrp", binding.etNRP.text.toString())
        myData.put("nama", binding.etNama.text.toString())
        myDB.update("mhs", myData,"nrp='" + binding.etNRP.text.toString() + "'",null)
        showToast("Data Terupdate")
      }
    }

    binding.btnHapus.setOnClickListener {
      myDB.delete("mhs","nrp='" + binding.etNRP.text.toString() + "'",null)
      binding.etNRP.text.clear()
      binding.etNama.text.clear()
      showToast("Data Terhapus")
    }
  }

  private fun validateInput(): Boolean {
    var isValidate = true
    if (binding.etNRP.text.isNullOrEmpty()) {
      binding.etNRP.error = "NRP harus diisi"
      isValidate = false
    }
    if (binding.etNama.text.isNullOrEmpty()) {
      binding.etNama.error = "Nama harus diisi"
      isValidate = false
    }
    return isValidate
  }

  private fun showToast (msg: String) {
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
  }
}