package com.finesaaa.browserapp

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SearchView
import com.finesaaa.browserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mainSrl.setOnRefreshListener {
      setupWebView()
    }

    setupWebView()
  }

  private fun setupWebView(url: String = "https://www.google.com/") {
    binding.mainSrl.isRefreshing = true

    binding.mainWebView.apply {
      settings.apply {
        javaScriptEnabled = true
      }

      loadUrl(url)
      webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
          binding.mainSrl.isRefreshing = false
        }
      }
    }
  }
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)

    (menu?.findItem(R.id.menu_search)?.actionView as SearchView).apply {
      val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView

      searchView.apply {
        setSearchableInfo(
          (getSystemService(Context.SEARCH_SERVICE) as SearchManager).getSearchableInfo(componentName)
        )
      }

      searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
          if (p0 != null) {
            if (p0.startsWith("http://") || p0.startsWith("https://")) {
              setupWebView(p0)
            } else {
              setupWebView("https://$p0")
            }
          }
          return false
        }

        override fun onQueryTextChange(p0: String?): Boolean {
          return false
        }
      })

      return true
    }
    return true
  }
}