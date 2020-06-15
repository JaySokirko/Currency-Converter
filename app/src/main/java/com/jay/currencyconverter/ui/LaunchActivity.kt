package com.jay.currencyconverter.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jay.currencyconverter.ui.nbuActivity.NbuActivity
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivity
import com.jay.currencyconverter.util.common.Constant.NBU_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.PREVIOUS_OPENED_ACTIVITY
import com.jay.currencyconverter.util.common.StorageManager

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (StorageManager.getVariable(PREVIOUS_OPENED_ACTIVITY, default = NBU_ACTIVITY)) {
            NBU_ACTIVITY -> startActivity(Intent(this, NbuActivity::class.java))
            ORGANIZATION_ACTIVITY -> startActivity(Intent(this, OrganizationActivity::class.java))
        }
    }
}