package com.finesaaa.gmapsapp

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.finesaaa.gmapsapp.databinding.ActivityMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener

class MapsActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMapsBinding
  private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
  private lateinit var locationListener: LocationListener
  private lateinit var focusedLocationClient: FusedLocationProviderClient
  private var gMap: GoogleMap? = null
  private var locationAction: () -> Unit = {}

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMapsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mapsCvBottom.visibility = View.GONE

    requestPermissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.forEach { (permit, granted) ->
          when (permit) {
            android.Manifest.permission.ACCESS_COARSE_LOCATION -> {
              if (!granted) {
                finish()
              } else {
                this.locationAction()
              }
            }
            android.Manifest.permission.ACCESS_FINE_LOCATION -> {
              if (!granted) {
                finish()
              } else {
                this.locationAction()
              }
            }
          }
        }
      }

    focusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    setupLocationMap()
    setupButtons()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.main_normal -> {
        gMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
      }
      R.id.main_hybird -> {
        gMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
      }
      R.id.main_sattelit -> {
        gMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
      }
      R.id.main_terrain -> {
        gMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
      }
      R.id.main_none -> {
        gMap?.mapType = GoogleMap.MAP_TYPE_NONE
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onStart() {
    getLocationPermissionFor {}
    super.onStart()
  }

  private fun setupLocationMap() {
    locationListener = LocationListener { loc -> setLocation(loc.latitude, loc.longitude) }

    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync { 
      this.gMap = it

      getLastLocation()
    }
  }

  private fun setupButtons() {
    binding.mainFabSearch.setOnClickListener {
      MainSearchDialog(
        object : MainSearchDialogParams {
          override fun onLatLongSearchClick() {
            LatLongSearchDialog(::setLocation).show(supportFragmentManager, null)
          }

          override fun onLastLocationClick() {
            getLastLocation()
          }

          override fun onRealLocationClick() {
            binding.mapsCvBottom.visibility = View.VISIBLE
            binding.mainFabSearch.visibility = View.GONE

            val locationUpdateCallback = object : LocationCallback() {
              override fun onLocationResult(lR: LocationResult) {
                lR.locations.forEach { loc: Location? ->
                  if (loc != null) {
                    setLocation(loc.latitude, loc.longitude)

                    binding.mapsTvLatitude.text = loc.latitude.toString()
                    binding.mapsTvLongitude.text = loc.longitude.toString()
                  }
                }
              }
            }
            val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
              interval = 10000
              fastestInterval = 5000
              priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED
                && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED
              ) {
                focusedLocationClient.requestLocationUpdates(
                  locationRequest,
                  locationUpdateCallback,
                  Looper.getMainLooper()
                )
              } else {
                getLocationPermissionFor {
                  focusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationUpdateCallback,
                    Looper.getMainLooper()
                  )
                }
              }
            } else {
              focusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationUpdateCallback,
                Looper.getMainLooper()
              )
            }

            binding.mapsBtnStopRealtime.setOnClickListener {
              binding.mapsCvBottom.visibility = View.GONE
              binding.mainFabSearch.visibility = View.VISIBLE

              focusedLocationClient.removeLocationUpdates(locationUpdateCallback)
            }
          }
        }
      ).show(supportFragmentManager, null)
    }
  }

  private fun getLastLocation() {
    val lastLocationSuccessListener = OnSuccessListener<Location> { loc: Location? ->
      if (loc != null) {
        setLocation(loc.latitude, loc.longitude)
      }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED
        && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED
      ) {
        focusedLocationClient.lastLocation.addOnSuccessListener(lastLocationSuccessListener)
      } else {
        getLocationPermissionFor {
          focusedLocationClient.lastLocation.addOnSuccessListener(lastLocationSuccessListener)
        }
      }
    } else {
      focusedLocationClient.lastLocation.addOnSuccessListener(lastLocationSuccessListener)
    }
  }

  private fun setLocation(latitude: Double, longitude: Double, zoom: Float = 8f) {
    val loc = LatLng(latitude, longitude)

    if (this.gMap != null) {
      Toast.makeText(this.applicationContext, "Lokasi Pilihan di " + loc.latitude + " : " + loc.longitude, Toast.LENGTH_LONG).show()
      this.gMap?.addMarker(MarkerOptions().position(loc).title("Lokasi Pilihan di " + loc.latitude + " : " + loc.longitude))
      this.gMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom))
    }
  }

  private fun getLocationPermissionFor(action: () -> Unit) {
    this.locationAction = action

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val permissions = arrayListOf<String>()

      if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
      }
      if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
      }

      if (permissions.size > 0) {
        requestPermissionLauncher.launch(permissions.toTypedArray())
      }
    } else {
      this.locationAction()
    }
  }
}