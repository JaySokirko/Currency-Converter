package com.jay.currencyconverter.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.jay.currencyconverter.R

class ErrorDialog : DialogFragment() {

    lateinit var errorDialogClickListener: ErrorDialogClickListener

    fun setOnErrorDialogClickListener(listener: ErrorDialogClickListener) {
        errorDialogClickListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)

        builder.apply {
            setTitle(resources.getString(R.string.error_dialog_title))
            setMessage(resources.getString(R.string.error_dialog_message))
            setIcon(ContextCompat.getDrawable(context, R.drawable.ic_error))
            setPositiveButton(resources.getString(R.string.error_dialog_positive_btn)) { _, _ ->
                errorDialogClickListener.onReload()
            }
            setNegativeButton(resources.getString(R.string.error_dialog_negative_btn)) { _, _ ->
                errorDialogClickListener.onExit()
            }
        }
        return builder.create()
    }

    interface ErrorDialogClickListener {
        fun onReload()
        fun onExit()
    }
}