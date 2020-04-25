package com.jay.currencyconverter.util

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.jay.currencyconverter.R

object Animator {

    fun collapseViewByWidth(view: View, targetWidth: Float){

    }

    fun expandViewByWidth(view: View, targetWidth: Float){

    }

    fun setViewBackgroundColor(view: View, color: Int, context: Context){
        view.backgroundTintList = ContextCompat.getColorStateList(context, color)
    }
}