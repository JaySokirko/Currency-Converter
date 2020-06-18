package com.jay.currencyconverter.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.jay.currencyconverter.R


class DialogBuilder : DialogFragment() {

    private lateinit var dialog: AlertDialog.Builder

    fun create(
        context: Context,
        icon: Drawable? = null,
        title: String? = null,
        message: String? = null,
        positiveBtnText: String? = null,
        onPositive: (() -> Unit)? = null
    ) {
        dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setIcon(icon)
            .setPositiveButton(positiveBtnText) { _, _ -> onPositive?.let { it() } }
            .setNegativeButton(context.resources.getString(R.string.cancel)) { _, _ -> dismiss() }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return dialog.create()
    }
}