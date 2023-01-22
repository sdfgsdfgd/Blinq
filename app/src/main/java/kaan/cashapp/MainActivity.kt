package kaan.cashapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kaan.cashapp.base.ViewBindActivity
import kaan.cashapp.databinding.ActivityMainBinding
import kaan.cashapp.ui.home.SharedViewModel

@AndroidEntryPoint
class MainActivity : ViewBindActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navController: NavController

    private val sharedVM by viewModels<SharedViewModel>() // Shared VM access for MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupNav()
    }

    private fun setupNav() {
        navController = findNavController(R.id.nav_host_fragment_activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}