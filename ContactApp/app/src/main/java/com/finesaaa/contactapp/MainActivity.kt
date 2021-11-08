package com.finesaaa.contactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
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

    setSupportActionBar(binding.mainToolbar)

    val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
    val navController = navHostFragment.navController
    val appBarConfiguration = AppBarConfiguration(
      topLevelDestinationIds = setOf(R.id.nav_contact_list_fragment),
      fallbackOnNavigateUpListener = ::onSupportNavigateUp
    )
    binding.mainToolbar.setupWithNavController(navController, appBarConfiguration)
  }
}