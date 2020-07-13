package com.jay.currencyconverter.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.google.android.material.navigation.NavigationView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivity
import com.jay.currencyconverter.util.common.Constant.OPENED_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION_ACTIVITY
import com.jay.currencyconverter.util.common.StorageManager
import util.ViewHelper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NavigationActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<OrganizationActivity>(OrganizationActivity::class.java)

    lateinit var navView: NavigationView

    @Before
    fun beforeEach() {
        navView = activityTestRule.activity.findViewById(R.id.navigation_view)
    }

    @Test
    fun navDrawer_navigationIsCorrect() {
        openNavDrawer()
        navigateToNbuActivity()

        onView(withId(R.id.nbu_activity_root_view)).check(matches(isDisplayed()))

        openNavDrawer()
        navigateToOrganizationActivity()

        onView(withId(R.id.organization_activity_root_view)).check(matches(isDisplayed()))
    }

    @Test
    fun navDraw_selectedMenuItem_isChecked() {
        assertEquals(true, isCurrentMenuItemChecked())

        openNavDrawer()
        navigateToNbuActivity()
        ViewHelper.waitTillAppear(navView) { assertEquals(true, isCurrentMenuItemChecked()) }

        openNavDrawer()
        navigateToOrganizationActivity()
        ViewHelper.waitTillAppear(navView) { assertEquals(true, isCurrentMenuItemChecked()) }

        pressBack()
        openNavDrawer()
        ViewHelper.waitTillAppear(navView) { assertEquals(true, isCurrentMenuItemChecked()) }

        pressBack()
        openNavDrawer()
        ViewHelper.waitTillAppear(navView) { assertEquals(true, isCurrentMenuItemChecked()) }
    }

    private fun openNavDrawer() {
        onView(withId(R.id.drawer_layout))
            .perform(DrawerActions.open())
    }

    private fun navigateToNbuActivity() {
        onView(withId(R.id.navigation_view))
            .perform(NavigationViewActions.navigateTo(R.id.nbu))
    }

    private fun navigateToOrganizationActivity() {
        onView(withId(R.id.navigation_view))
            .perform(NavigationViewActions.navigateTo(R.id.organization))
    }

    private fun lastOpenedMenuItemPosition() =
        StorageManager.getVariable(OPENED_ACTIVITY, ORGANIZATION_ACTIVITY)

    private fun isCurrentMenuItemChecked() =
        navView.menu.getItem(lastOpenedMenuItemPosition()).isChecked

}