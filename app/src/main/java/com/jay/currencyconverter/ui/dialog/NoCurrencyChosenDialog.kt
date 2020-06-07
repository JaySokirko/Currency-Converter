package com.jay.currencyconverter.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.jay.currencyconverter.R

class NoCurrencyChosenDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setMessage(resources.getString(R.string.currency_should_chosen_message))

            setIcon(ContextCompat.getDrawable(context, R.drawable.ic_error))

            setPositiveButton(resources.getString
                (R.string.currency_should_chosen_positive_btn_text)) { _, _ -> dismiss() }
        }

        return builder.create()
    }
}