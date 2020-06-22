package com.jay.currencyconverter.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jay.currencyconverter.ui.nbuActivity.NbuActivity
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivity
import com.jay.currencyconverter.util.common.Constant.NBU_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION_ACTIVITY
import com.jay.currencyconverter.util.common.Constant.OPENED_ACTIVITY
import com.jay.currencyconverter.util.common.StorageManager

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (StorageManager.getVariable(OPENED_ACTIVITY, default = ORGANIZATION_ACTIVITY)) {
            NBU_ACTIVITY -> {
                startActivity(Intent(this, NbuActivity::class.java)
                                  .addFlags(FLAG_ACTIVITY_CLEAR_TASK or
                                                    FLAG_ACTIVITY_NEW_TASK))
            }
            ORGANIZATION_ACTIVITY -> {
                startActivity(Intent(this, OrganizationActivity::class.java)
                                  .addFlags(FLAG_ACTIVITY_CLEAR_TASK or
                                                    FLAG_ACTIVITY_NEW_TASK))
            }
        }
    }
}