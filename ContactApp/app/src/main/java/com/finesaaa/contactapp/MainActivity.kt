package com.finesaaa.contactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.finesaaa.contactapp.databinding.ActivityMainBinding
import com.finesaaa.contactapp.db.sqlite.ContactDatabase
import com.finesaaa.contactapp.dialog.AddContactDialog

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var contactListAdapter: ContactListAdapter
  private lateinit var contactDatabase: ContactDatabase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    contactDatabase = ContactDatabase(this, arrayListOf(ContactModel()))

    setupList()
    setupButtons()
  }

  private fun setupButtons() {
    binding.fabAdd.setOnClickListener {
      AddContactDialog { data ->
        if (data == null) {
          showToast("Isi data dengan benar!")
        } else {
//          showToast("Masuk!")
          contactListAdapter.addItem(data)
          contactDatabase.insert(data)
        }
      }.show(supportFragmentManager, null)
    }
  }

  private fun setupList() {
    contactListAdapter = ContactListAdapter()
    binding.rvContact.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = this@MainActivity.contactListAdapter
    }

    val contactList = contactDatabase.read(ContactModel(), null)
    if (contactList.size >  0) {
      contactListAdapter.updateAllData(contactList)
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
  }
}