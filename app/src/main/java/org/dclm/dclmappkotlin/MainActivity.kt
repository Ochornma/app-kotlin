package org.dclm.dclmappkotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAppBarConfiguration:AppBarConfiguration
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        var navigation = findViewById<NavigationView>(R.id.nav_view)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navHostFragment!!.navController
        )
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment, R.id.subscribeFragment, R.id.searchFragment, R.id.giveFragment, R.id.notificationFragment)
            .setOpenableLayout(drawer_layout)
            .build()
        val navController = Navigation.findNavController(this, R.id.main_nav_host)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration)
        NavigationUI.setupWithNavController(navigation, navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            val id = destination.id
            if (id == R.id.homeFragment || id == R.id.connectFragment || id == R.id.blogFragment || id == R.id.podcastFragment || id == R.id.videoDemandFragment) {
                bottomNavigationView.visibility = View.VISIBLE
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else if (id == R.id.subscribeFragment) {
                bottomNavigationView.visibility = View.GONE
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                bottomNavigationView.visibility = View.GONE
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navcontroller = Navigation.findNavController(this, R.id.main_nav_host)
        return NavigationUI.navigateUp(navcontroller, mAppBarConfiguration)
    }
}