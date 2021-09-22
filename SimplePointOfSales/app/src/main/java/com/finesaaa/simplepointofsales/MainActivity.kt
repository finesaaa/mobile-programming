package com.finesaaa.simplepointofsales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.finesaaa.simplepointofsales.adapter.InvoiceAdapter
import com.finesaaa.simplepointofsales.databinding.ActivityMainBinding
import com.finesaaa.simplepointofsales.model.Invoice


class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var rvInvoiceAdapter: InvoiceAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)

    setContentView(binding.root)
    supportActionBar!!.title = resources.getString(R.string.app_name)

    setupAdapter()
    setupView()

    binding.btnProses.setOnClickListener {
      if (validateInput()) {
        val invoice = Invoice (
          namaPembeli = binding.etNamaPelanggan.text.toString().trim { it <= ' ' },
          namaBarang = binding.etNamaBarang.text.toString().trim { it <= ' ' },
          jmlBarang = binding.etJmlBarang.text.toString().toDouble(),
          harga = binding.etHarga.text.toString().toDouble(),
          jmlUang = binding.etJmlUang.text.toString().toDouble(),
        )
        rvInvoiceAdapter.addData(invoice)
      }
    }

    binding.btnHapus.setOnClickListener {
      rvInvoiceAdapter.deleteLastData()
      Toast.makeText(applicationContext, "Data sudah dihapus", Toast.LENGTH_SHORT).show()
    }

    binding.btnKeluar.setOnClickListener {
      moveTaskToBack(true)
    }
  }

  private fun setupAdapter() {
    binding.rvOutputTransaksi.setHasFixedSize(true)
    rvInvoiceAdapter = InvoiceAdapter()
  }

  private fun setupView() {
    binding.rvOutputTransaksi.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = rvInvoiceAdapter
    }
  }

  private fun validateInput() : Boolean {
    var validate = true
    if (binding.etNamaPelanggan.text.isNullOrEmpty()) {
      binding.etNamaPelanggan.error = "Nama pelanggan tidak boleh kosong"
      validate = false
    }
    if (binding.etNamaBarang.text.isNullOrEmpty()) {
      binding.etNamaBarang.error = "Nama barang tidak boleh kosong"
      validate = false
    }
    if (binding.etJmlBarang.text.isNullOrEmpty()) {
      binding.etJmlBarang.error = "Jumlah barang tidak boleh kosong"
      validate = false
    }
    if (binding.etHarga.text.isNullOrEmpty()) {
      binding.etHarga.error = "Harga tidak boleh kosong"
      validate = false
    }
    if (binding.etJmlUang.text.isNullOrEmpty()) {
      binding.etJmlUang.error = "Uang yang dibayar tidak boleh kosong"
      validate = false
    }
    return validate
  }
}