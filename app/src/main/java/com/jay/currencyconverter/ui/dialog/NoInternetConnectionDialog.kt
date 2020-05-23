package com.jay.currencyconverter.ui.dialog


import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.jay.currencyconverter.R

class NoInternetConnectionDialog : DialogFragment() {

    lateinit var clickListener: NoInternetConnectionDialogClickListener

    fun setNoInternetConnectionDialogClickListener(listener: NoInternetConnectionDialogClickListener) {
        clickListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        builder.apply {
            setTitle("No internet connection")
            setIcon(ContextCompat.getDrawable(context, R.drawable.ic_no_internet))
            setPositiveButton("Settings") { _, _ -> clickListener.openSettings() }
            setNegativeButton("Exit") { _, _ -> clickListener.exit() }
        }

        return builder.create()
    }

    interface NoInternetConnectionDialogClickListener {
        fun openSettings()
        fun exit()
    }
}