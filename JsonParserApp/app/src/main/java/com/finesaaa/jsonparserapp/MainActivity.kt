package com.finesaaa.jsonparserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.finesaaa.jsonparserapp.databinding.ActivityMainBinding

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
      topLevelDestinationIds = setOf(R.id.nav_cases_list_fragment),
      fallbackOnNavigateUpListener = ::onSupportNavigateUp
    )
    binding.mainToolbar.setupWithNavController(navController, appBarConfiguration)
  }
}