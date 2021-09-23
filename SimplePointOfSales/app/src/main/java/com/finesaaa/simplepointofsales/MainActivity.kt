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
          namaBarang = binding.etNamaBarang.text.toString().trim { it <= ' ' },
          jmlBarang = binding.etJmlBarang.text.toString().toDouble(),
          harga = binding.etHarga.text.toString().toDouble(),
        )
        rvInvoiceAdapter.addData(invoice)
        binding.tvTotal.text = getTotalPayment(rvInvoiceAdapter.getAllData()).toString()
      }
    }

    binding.btnHapus.setOnClickListener {
      rvInvoiceAdapter.clearAllData()
      Toast.makeText(applicationContext, "Data sudah dihapus", Toast.LENGTH_SHORT).show()
    }

    binding.btnBayar.setOnClickListener {
      if (validateInputPayment()) {
        var totalPay = getTotalPayment(rvInvoiceAdapter.getAllData())
        var returnPay = binding.etJmlUang.text.toString().toDouble() - totalPay

        binding.tvKembalian.text = if (returnPay >= 0.0) {
          returnPay.toString()
        } else {
          "Rp 0"
        }

        binding.tvBonus.text = getBonusStatus(totalPay)
        binding.tvKeterangan.text = getReturnStatus(returnPay)
      }
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
    return validate
  }

  private fun validateInputPayment() : Boolean {
    var validate = true
    if (binding.etNamaPelanggan.text.isNullOrEmpty()) {
      binding.etNamaPelanggan.error = "Nama pembeli tidak boleh kosong"
      validate = false
    }
    if (binding.etJmlUang.text.isNullOrEmpty()) {
      binding.etJmlUang.error = "Jumlah uang yang dibayar tidak boleh kosong"
      validate = false
    }
    return validate
  }

  private fun getReturnStatus(returnPay: Double): String {
    return if (returnPay < 0.0) {
      "Uang bayar kurang Rp. -${returnPay}"
    } else if (returnPay > 0.0) {
      "Lunas dengan kembalian Rp. ${returnPay}"
    } else {
      "Lunas"
    }
  }

  private fun getBonusStatus(total: Double): String {
    return if (total >= 200000) {
      "HardDisk 1TB"
    } else if (total >= 50000) {
      "Keyboard Gaming"
    } else if (total >= 40000) {
      "Mouse Gaming"
    } else {
      "Tidak ada bonus!"
    }
  }

  private fun getTotalPayment(invoiceList: ArrayList<Invoice>) : Double {
    var total = 0.0
    invoiceList.forEach { item ->
      total += item.getTotal()
    }
    return total
  }

}