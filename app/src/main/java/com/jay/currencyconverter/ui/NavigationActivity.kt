package com.jay.currencyconverter.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.jakewharton.rxbinding.view.RxMenuItem
import com.jay.currencyconverter.BuildConfig
import com.jay.currencyconverter.R
import com.jay.currencyconverter.ui.dialog.DialogBuilder
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.ui.dialog.NoInternetConnectionDialog
import com.jay.currencyconverter.ui.nbuActivity.NbuActivity
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivity
import com.jay.currencyconverter.ui.settingsActivity.SettingsActivity
import com.jay.currencyconverter.util.common.Constant.ENGLISH_LANGUAGE
import com.jay.currencyconverter.util.common.Constant.NBU_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.OPENED_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.SELECTED_LANGUAGE
import com.jay.currencyconverter.util.common.Constant.UKRAINIAN_LANGUAGE
import com.jay.currencyconverter.util.common.InternetConnection
import com.jay.currencyconverter.util.common.StorageManager
import com.jay.currencyconverter.util.ui.Localization
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit


abstract class NavigationActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    NoInternetConnectionDialog.OnDialogButtonsClickListener {

    protected val errorDialog = ErrorDialog()
    protected val noInternetConnectionDialog = NoInternetConnectionDialog()
    protected val dialogBuilder: DialogBuilder = DialogBuilder()
    protected var isDataAlreadyLoaded = false
    protected var mainContentView: View? = null

    private var nawDrawer: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var navHeaderAnimation: AnimationDrawable? = null
    private var actionBarItemClickListener: ActionBarItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_navigation)

        noInternetConnectionDialog.setOnDialogButtonsClickListener(this)
        navigationMenuCheckedItemPositions.push(StorageManager.getVariable(OPENED_ACTIVITY,
                ORGANIZATION_ACTIVITY))

        nawDrawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        initDrawerHeader()
    }

    override fun onResume() {
        navHeaderAnimation?.start()
        setCheckedNavigationViewItem(navigationMenuCheckedItemPositions.peek())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        navHeaderAnimation?.stop()
    }

    override fun onBackPressed() {
        if (nawDrawer?.isDrawerOpen(GravityCompat.START) == true) {
            nawDrawer?.closeDrawer(GravityCompat.START)
        } else {
            navigationMenuCheckedItemPositions.pop()
            super.onBackPressed()
        }
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
                launchSettingsActivity()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.update -> {
                RxMenuItem.clicks(item)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        actionBarItemClickListener ?: throw NullPointerException("listener must be set")
                        actionBarItemClickListener?.onUpdate()
                    }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**@see NoInternetConnectionDialog.OnDialogButtonsClickListener.openSettings*/
    override fun openSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    protected fun setActionBarItemClickListener(listener: ActionBarItemClickListener){
        actionBarItemClickListener = listener
    }

    protected fun checkInternetConnection(onIsConnected : () -> Unit) {
        if (!InternetConnection.isInternetConnectionEnabled()){
            noInternetConnectionDialog.show(supportFragmentManager, this.localClassName)
        } else {
            onIsConnected()
        }
    }

    protected fun initContent(contentLayoutId: Int, toolbarLayoutId: Int = R.layout.default_toolbar) {
        val mainContainer: FrameLayout = findViewById(R.id.main_container)
        mainContentView = inflater?.inflate(contentLayoutId, mainContainer, false)
        mainContainer.addView(mainContentView)

        val toolBarContainer: FrameLayout = findViewById(R.id.app_bar_container)
        val toolBarContentView: View? = inflater?.inflate(toolbarLayoutId, toolBarContainer, false)
        toolBarContainer.addView(toolBarContentView)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);
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
        }
    }

    private fun initDrawerHeader( ) {
        val headerView: View? = navigationView?.getHeaderView(0)
        val parentView: ConstraintLayout? = headerView?.findViewById(R.id.draw_header_parent)

        navHeaderAnimation = parentView?.background as AnimationDrawable
        navHeaderAnimation?.setEnterFadeDuration(5000)
        navHeaderAnimation?.setExitFadeDuration(2000)
        navHeaderAnimation?.start()

        headerView.findViewById<TextView>(R.id.version).text = BuildConfig.VERSION_NAME
    }

    private fun launchNbuActivity() {
        StorageManager.saveVariable(OPENED_ACTIVITY, NBU_ACTIVITY)

        nawDrawer?.closeDrawer(GravityCompat.START)

        startActivity(Intent(this, NbuActivity::class.java)
                          .addFlags(FLAG_ACTIVITY_SINGLE_TOP))
    }

    private fun launchOrganizationActivity() {
        StorageManager.saveVariable(OPENED_ACTIVITY, ORGANIZATION_ACTIVITY)

        nawDrawer?.closeDrawer(GravityCompat.START)

        startActivity(Intent(this, OrganizationActivity::class.java)
                          .addFlags(FLAG_ACTIVITY_SINGLE_TOP))
    }

    private fun launchSettingsActivity() {
        nawDrawer?.closeDrawer(GravityCompat.START)

        startActivity(Intent(this, SettingsActivity::class.java)
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

    private fun setCheckedNavigationViewItem(position: Int){
        navigationView?.menu?.getItem(position)?.isChecked = true
    }

    interface ActionBarItemClickListener {
        fun onUpdate()
    }

    companion object {
        private val navigationMenuCheckedItemPositions: Stack<Int> = Stack()
    }
}

