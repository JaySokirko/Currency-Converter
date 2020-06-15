package com.jay.currencyconverter.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.jay.currencyconverter.BuildConfig
import com.jay.currencyconverter.R
import com.jay.currencyconverter.ui.nbuActivity.NbuActivity
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivity


abstract class NavigationActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var mainContentView: View
    private lateinit var nawDrawer: DrawerLayout
    private lateinit var inflater: LayoutInflater
    private lateinit var navigationView: NavigationView
    private var backgroundAnimation: AnimationDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        nawDrawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        initDrawerHeader()
    }

    override fun onResume() {
        super.onResume()
        backgroundAnimation?.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundAnimation?.stop()
    }

    protected fun initContent(contentLayoutId: Int, toolbarLayoutId: Int) {
        val mainContainer: FrameLayout = findViewById(R.id.main_container)
        mainContentView = inflater.inflate(contentLayoutId, mainContainer, false)
        mainContainer.addView(mainContentView)

        val toolBarContainer: FrameLayout = findViewById(R.id.app_bar_container)
        val toolBarContentView: View = inflater.inflate(toolbarLayoutId, toolBarContainer, false)
        toolBarContainer.addView(toolBarContentView)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        initNavigation(toolbar)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nbu -> {
                startActivity(Intent(this, NbuActivity::class.java))
            }
            R.id.organization -> {
                startActivity(Intent(this, OrganizationActivity::class.java))
            }
            R.id.en_lang -> {
                //set en lang
            }
            R.id.ua_lang -> {

            }
            R.id.settings -> {

            }
        }
        return true
    }

    override fun onBackPressed() {
        if (nawDrawer.isDrawerOpen(GravityCompat.START)) {
            nawDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initNavigation(toolbar: Toolbar) {
        ActionBarDrawerToggle(this,
                              nawDrawer,
                              toolbar,
                              R.string.navigation_drawer_open,
                              R.string.navigation_drawer_close)
            .apply {
            drawerArrowDrawable.color = resources.getColor(R.color.white)
            syncState()
        }

        navigationView.apply {
            setNavigationItemSelectedListener(this@NavigationActivity)
            itemIconTintList = null
        }
    }

    private fun initDrawerHeader( ) {
        val headerView: View = navigationView.getHeaderView(0)
        val parentView: ConstraintLayout = headerView.findViewById(R.id.draw_header_parent)

        backgroundAnimation = parentView.background as AnimationDrawable
        backgroundAnimation?.setEnterFadeDuration(5000)
        backgroundAnimation?.setExitFadeDuration(2000)
        backgroundAnimation?.start()

        headerView.findViewById<TextView>(R.id.version).text = BuildConfig.VERSION_NAME
    }
}

