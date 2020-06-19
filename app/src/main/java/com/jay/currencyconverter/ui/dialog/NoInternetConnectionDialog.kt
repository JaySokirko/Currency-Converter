package com.jay.currencyconverter.ui.dialog


import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.jay.currencyconverter.R
import kotlin.system.exitProcess


class NoInternetConnectionDialog : DialogFragment() {

    private lateinit var listener: OnDialogButtonsClickListener

    fun setOnDialogButtonsClickListener(listener: OnDialogButtonsClickListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        isCancelable = false

        builder.apply {
            setTitle(getString(R.string.no_internet_connection))

            setIcon(ContextCompat.getDrawable(context, R.drawable.ic_no_internet))

            setPositiveButton(getString(R.string.open_settings)) { _, _ -> listener.openSettings() }

            setNegativeButton(getString(R.string.exit)) { _, _ -> exitProcess(0) }
        }

        return builder.create()
    }

    interface OnDialogButtonsClickListener {
        fun openSettings()
    }
}