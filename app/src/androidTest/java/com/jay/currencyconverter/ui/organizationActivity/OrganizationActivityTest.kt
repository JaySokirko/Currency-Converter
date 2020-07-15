package com.jay.currencyconverter.ui.organizationActivity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.jay.currencyconverter.R
import org.junit.Rule
import org.junit.Test

class OrganizationActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<OrganizationActivity>(OrganizationActivity::class.java)

    @Test
    fun navigateToBottomButton_appearsOnScrollDown() {
        onView(withId(R.id.organization_activity_root_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.scroll_to_bottom_btn)).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToTopButton_appearsOnScrollUp() {
        onView(withId(R.id.organization_activity_root_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.organization_activity_root_view)).perform(ViewActions.swipeDown())
        onView(withId(R.id.scroll_to_top_btn)).check(matches(isDisplayed()))
    }

    @Test
    fun typeOnOrganizationElement_opensCalculatorActivity() {

    }
    
}