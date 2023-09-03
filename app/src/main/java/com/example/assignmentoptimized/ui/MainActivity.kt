package com.example.assignmentoptimized.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.example.assignmentoptimized.R
import com.example.assignmentoptimized.databinding.ActivityMainBinding
import com.example.assignmentoptimized.viewmodels.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            Log.d("*****", "icon")
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navigationView.menu.findItem(R.id.DarkTheme).isChecked = resources.configuration.isNightModeActive
        binding.navigationView.menu.findItem(R.id.LightTheme).isChecked = !resources.configuration.isNightModeActive
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.DarkTheme -> {
                    //menuItem.isChecked = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    this.recreate()
                }
                R.id.LightTheme -> {
                    //menuItem.isChecked = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    this.recreate()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }
}