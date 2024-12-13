package com.example.greenway

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.greenway.databinding.ActivityMainBinding
import com.example.greenway.screens.home.HomeFragment
import com.example.greenway.utils.APP_ACTIVITY

class MainActivity : AppCompatActivity() {
    lateinit var toolbar : Toolbar
    lateinit var navController: NavController
    private var _binding : ActivityMainBinding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        toolbar = mBinding.toolbar
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        setSupportActionBar(toolbar)
        title = getString(R.string.app_name)


        val bottomNavigationView = mBinding.bottomNavigation
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = destination.label
            invalidateOptionsMenu()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar, menu)

        val currentDestination = navController.currentDestination?.id
        val menuItem = menu?.findItem(R.id.action_connect)

        menuItem?.isVisible = (currentDestination == R.id.home)

        val iconDrawable = menuItem?.icon
        val isConnected = false
        val color = if (isConnected) resources.getColor(R.color.text_primary) else resources.getColor(R.color.error_red)
        iconDrawable?.setTint(color)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_connect -> {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                val homeFragment = navHostFragment?.childFragmentManager?.fragments?.firstOrNull() as HomeFragment
                homeFragment.connect()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}