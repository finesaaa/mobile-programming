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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}