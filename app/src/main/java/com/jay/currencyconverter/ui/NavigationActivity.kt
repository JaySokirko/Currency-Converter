package com.jay.currencyconverter.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.ui.bankActivity.BankActivity
import com.jay.currencyconverter.ui.nbuActivity.NbuActivity
import com.jay.currencyconverter.ui.newsActivity.NewsActivity

abstract class NavigationActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var mainContentView: View
    private lateinit var nawDrawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        nawDrawer = findViewById(R.id.drawer_layout)
    }

    protected fun initContent(contentLayoutId: Int, toolbarLayoutId: Int) {
        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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
//            R.id.foreign -> {
//                setLanguage(resources.getString(R.string.culture_foreign))
//            }
//            R.id.eng -> {
//                setLanguage(resources.getString(R.string.culture_en))
//            }
//            R.id.help -> {
//                startActivity(Intent(this, ManualActivity::class.java).putExtra(FRAGMENT_KEY, FRAGMENT_TYPE_HELP))
//            }
            R.id.nbu -> {
                startActivity(Intent(this, NbuActivity::class.java))
            }
            R.id.bank -> {
                startActivity(Intent(this, BankActivity::class.java))
            }
            R.id.news -> {
                startActivity(Intent(this, NewsActivity::class.java))
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

//    protected open fun translateUi() {
//        LocalStorage.saveVariable(LocalStorageManager.LANGUAGE, language)
//        translateUi()
//    }
//
//    protected open fun setLanguage(language: String) {
//        finish()
//        startActivity(intent)
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(NavigationActivity.LANGUAGE_CHANGED, true)
//    }

    private fun initNavigation(toolbar: Toolbar) {
        val navDrawOpenResId: Int = R.string.navigation_drawer_open
        val navDrawCloseResId: Int = R.string.navigation_drawer_close
        val toggle =
            ActionBarDrawerToggle(this, nawDrawer, toolbar, navDrawOpenResId, navDrawCloseResId)
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.drawer_menu_layout)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.itemIconTintList = null
    }

}

