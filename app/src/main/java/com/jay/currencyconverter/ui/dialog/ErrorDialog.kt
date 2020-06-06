package com.jay.currencyconverter.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.jay.currencyconverter.R

class ErrorDialog : DialogFragment() {

    private lateinit var onDialogButtonsClickListener: OnDialogButtonsClickListener

    fun setOnDialogButtonsClickListener(listener: OnDialogButtonsClickListener) {
        onDialogButtonsClickListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setTitle(resources.getString(R.string.error_dialog_title))

            setMessage(resources.getString(R.string.error_dialog_message))

            setIcon(ContextCompat.getDrawable(context, R.drawable.ic_error))

            setPositiveButton(resources.getString(R.string.error_dialog_positive_btn)) { _, _ ->
                onDialogButtonsClickListener.onReload()
            }
            setNegativeButton(resources.getString(R.string.error_dialog_negative_btn)) { _, _ ->
                onDialogButtonsClickListener.onExit()
            }
        }
        return builder.create()
    }

    interface OnDialogButtonsClickListener {
        fun onReload()
        fun onExit()
    }
}