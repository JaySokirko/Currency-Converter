package com.jay.currencyconverter.ui

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.provider.Settings
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
import com.jay.currencyconverter.ui.dialog.DialogBuilder
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.ui.dialog.NoInternetConnectionDialog
import com.jay.currencyconverter.ui.nbuActivity.NbuActivity
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivity
import com.jay.currencyconverter.util.common.Constant.ENGLISH_LANGUAGE
import com.jay.currencyconverter.util.common.Constant.NBU_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.PREVIOUS_OPENED_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.SELECTED_LANGUAGE
import com.jay.currencyconverter.util.common.Constant.UKRAINIAN_LANGUAGE
import com.jay.currencyconverter.util.common.InternetConnection
import com.jay.currencyconverter.util.common.StorageManager
import com.jay.currencyconverter.util.ui.Localization


abstract class NavigationActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    NoInternetConnectionDialog.OnDialogButtonsClickListener {

    protected val errorDialog = ErrorDialog()
    protected val noInternetConnectionDialog = NoInternetConnectionDialog()
    protected val dialogBuilder: DialogBuilder = DialogBuilder()
    protected var mainContentView: View? = null
    private var nawDrawer: DrawerLayout? = null
    private var inflater: LayoutInflater? = null
    private var navigationView: NavigationView? = null
    private var backgroundAnimation: AnimationDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        Localization.setLocale(this, Localization.language)
        noInternetConnectionDialog.setOnDialogButtonsClickListener(this)

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nbu -> {
                launchNbuActivity()
            }
            R.id.organization -> {
                launchOrganizationActivity()
            }
            R.id.en_lang -> {
                setEnglishLanguage()
            }
            R.id.ua_lang -> {
                setUkrainianLanguage()
            }
            R.id.settings -> {

            }
        }
        return true
    }

    override fun onBackPressed() {
        if (nawDrawer?.isDrawerOpen(GravityCompat.START) == true) {
            nawDrawer?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**@see NoInternetConnectionDialog.OnDialogButtonsClickListener.openSettings*/
    override fun openSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    protected fun checkInternetConnection(onIsConnected : () -> Unit) {
        if (!InternetConnection.isInternetConnectionEnabled()){
            noInternetConnectionDialog.show(supportFragmentManager, this.localClassName)
        } else {
            onIsConnected()
        }
    }

    protected fun initContent(contentLayoutId: Int, toolbarLayoutId: Int) {
        val mainContainer: FrameLayout = findViewById(R.id.main_container)
        mainContentView = inflater?.inflate(contentLayoutId, mainContainer, false)
        mainContainer.addView(mainContentView)

        val toolBarContainer: FrameLayout = findViewById(R.id.app_bar_container)
        val toolBarContentView: View? = inflater?.inflate(toolbarLayoutId, toolBarContainer, false)
        toolBarContainer.addView(toolBarContentView)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        initNavigation(toolbar)
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

        navigationView?.apply {
            setNavigationItemSelectedListener(this@NavigationActivity)
            itemIconTintList = null
            menu.getItem(StorageManager.getVariable(PREVIOUS_OPENED_ACTIVITY,
                    default = NBU_ACTIVITY)).isChecked = true
        }
    }

    private fun initDrawerHeader( ) {
        val headerView: View? = navigationView?.getHeaderView(0)
        val parentView: ConstraintLayout? = headerView?.findViewById(R.id.draw_header_parent)

        backgroundAnimation = parentView?.background as AnimationDrawable
        backgroundAnimation?.setEnterFadeDuration(5000)
        backgroundAnimation?.setExitFadeDuration(2000)
        backgroundAnimation?.start()

        headerView.findViewById<TextView>(R.id.version).text = BuildConfig.VERSION_NAME
    }

    private fun launchNbuActivity() {
        StorageManager.saveVariable(PREVIOUS_OPENED_ACTIVITY, NBU_ACTIVITY)
        nawDrawer?.closeDrawer(GravityCompat.START)
        startActivity(Intent(this, NbuActivity::class.java).addFlags(FLAG_ACTIVITY_SINGLE_TOP))
    }

    private fun launchOrganizationActivity() {
        StorageManager.saveVariable(PREVIOUS_OPENED_ACTIVITY, ORGANIZATION_ACTIVITY)
        nawDrawer?.closeDrawer(GravityCompat.START)
        startActivity(Intent(this, OrganizationActivity::class.java)
                          .addFlags(FLAG_ACTIVITY_SINGLE_TOP))
    }

    private fun setEnglishLanguage() {
        nawDrawer?.closeDrawer(GravityCompat.START)

        val englishAlreadyChosen: Boolean = StorageManager.getVariable(
            SELECTED_LANGUAGE, UKRAINIAN_LANGUAGE) == ENGLISH_LANGUAGE

        if (englishAlreadyChosen) return

        Localization.setLocale(this, ENGLISH_LANGUAGE)
        recreate()
    }

    private fun setUkrainianLanguage() {
        nawDrawer?.closeDrawer(GravityCompat.START)

        val ukrainianAlreadyChosen = StorageManager.getVariable(
            SELECTED_LANGUAGE, UKRAINIAN_LANGUAGE) == UKRAINIAN_LANGUAGE

        if (ukrainianAlreadyChosen) return

        Localization.setLocale(this, UKRAINIAN_LANGUAGE)
        recreate()
    }
}

