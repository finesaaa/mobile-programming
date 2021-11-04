package com.finesaaa.simplepointofsales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.finesaaa.simplepointofsales.adapter.ProductAdapter
import com.finesaaa.simplepointofsales.databinding.ActivityMainBinding
import com.finesaaa.simplepointofsales.db.sqlite.OperationsDatabase
import com.finesaaa.simplepointofsales.model.Customer
import com.finesaaa.simplepointofsales.model.Invoice
import com.finesaaa.simplepointofsales.model.Product
import com.finesaaa.simplepointofsales.model.Transaction


class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var rvProductAdapter: ProductAdapter
  private lateinit var transactionDatabase: OperationsDatabase

  companion object {
    const val dbLog = "SimplePointOfSalesDB"
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)

    setContentView(binding.root)
    supportActionBar!!.title = resources.getString(R.string.app_name)

    transactionDatabase = OperationsDatabase(this, arrayListOf(
      Product(),
      Customer(),
      Transaction(),
      Invoice()
    ))
    setupAdapter()
    setupButtons()
  }

  private fun setupAdapter() {
    rvProductAdapter = ProductAdapter()
    binding.rvOutputTransaksi.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = rvProductAdapter
    }
  }

  private fun setupButtons() {

    binding.btnProses.setOnClickListener {
      if (validateInput()) {
        val product = Product (
          nama = binding.etNamaBarang.text.toString(),
          jumlah = binding.etJmlBarang.text.toString().toInt(),
          harga = binding.etHarga.text.toString().toDouble(),
        )
        rvProductAdapter.addData(product)
        binding.tvTotal.text = getTotalPayment(rvProductAdapter.getAllData()).toString()
      }
    }

    binding.btnHapus.setOnClickListener {
      rvProductAdapter.clearAllData()
      showToast("Data sudah dihapus")

      binding.tvKeterangan.text = ""
      binding.tvBonus.text = ""
      binding.tvKembalian.text = ""
      binding.tvTotal.text = ""
    }

    binding.btnBayar.setOnClickListener {
      if (validateInputPayment()) {
        val products = rvProductAdapter.getAllData()
        var totalPay = getTotalPayment(products)
        var returnPay = binding.etJmlUang.text.toString().toDouble() - totalPay

        binding.tvKembalian.text = if (returnPay >= 0.0) {
          returnPay.toString()
        } else {
          "Rp 0"
        }

        binding.tvBonus.text = getBonusStatus(totalPay)
        binding.tvKeterangan.text = getReturnStatus(returnPay)

        val idPelanggan = transactionDatabase.insert(
          Customer(
            nama = binding.etNamaPelanggan.text.toString()
          )
        )

        val idPembayaran = transactionDatabase.insert(
          Invoice(
            idPelanggan = idPelanggan,
            uangBayar = binding.etJmlUang.text.toString().toDouble(),
            totalPembelian = totalPay,
            kembalian = returnPay,
            bonus = binding.tvBonus.text.toString(),
            keterangan = binding.tvKeterangan.toString()
          )
        )

        val idListProduk = arrayListOf<Long>()
        products.forEach {
          idListProduk.add(transactionDatabase.insert(it))
        }

        idListProduk.forEach { idProduk ->
          val idTransaksi = transactionDatabase.insert(
            Transaction(
              idProduk = idProduk,
              idPembayaran = idPembayaran
            )
          )
        }
      }
    }

    binding.btnLihat.setOnClickListener {
      if (validateGetCustomer()) {
        val pelanggan: Customer
        var result: Any?
        result = transactionDatabase.read(
          Customer(),
          null,
          "WHERE nama LIKE '%${binding.etNamaPelanggan.text}%'"
        )

        if (result.size > 0) {
          pelanggan = result[0]
        } else {
          showToast("Pelanggan tidak ditemukan")
          return@setOnClickListener
        }

        val pembayaran: Invoice
        result = transactionDatabase.read(
          Invoice(),
          null,
          "WHERE idPelanggan='${pelanggan.idPelanggan}'"
        )
        if (result.size > 0) {
          pembayaran = result[0]
        } else {
          showToast("Transaksi tidak ditemukan")
          return@setOnClickListener
        }

        binding.etJmlUang.setText(pembayaran.uangBayar.toString())
        binding.tvTotal.text = pembayaran.uangBayar.toString()
        binding.tvKembalian.text = pembayaran.kembalian.toString()
        binding.tvBonus.text = pembayaran.bonus
        binding.tvKeterangan.text = pembayaran.keterangan

        val listTransaksi = arrayListOf<Transaction>()
        result = transactionDatabase.read(
          Transaction(),
          null,
          "WHERE id_transaksi='${pembayaran.idPembayaran}'"
        )
        if (result.size > 0) {
          result.forEach { transaksi ->
            listTransaksi.add(transaksi)
          }
        } else {
          return@setOnClickListener
        }

        val listProduk = arrayListOf<Product>()
        listTransaksi.forEach { transaksi ->
          val produk = transactionDatabase.read(Product(), transaksi.idProduk)
          if (produk.size > 0) {
            listProduk.add(transactionDatabase.read(Product(), transaksi.idProduk)[0])
          }
        }

        rvProductAdapter.updateAllData(listProduk)
      }
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

  private fun validateGetCustomer(): Boolean {
    var validate = true
    if (binding.etNamaPelanggan.text.isNullOrEmpty()) {
      binding.etNamaPelanggan.error = "Nama pembeli tidak boleh kosong"
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

  private fun getTotalPayment(invoiceList: ArrayList<Product>) : Double {
    var total = 0.0
    invoiceList.forEach { item ->
      total += item.getTotalPrice()
    }
    return total
  }

  private fun showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
  }
}