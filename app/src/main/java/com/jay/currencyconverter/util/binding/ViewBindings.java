package com.jay.currencyconverter.util.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class ViewBindings {

    @BindingAdapter("isVisible")
    public static void setIsVisible(View view, boolean isVisible) {
        if (isVisible) {
            view.animate().alpha(1.0f).setDuration(300).start();
            view.setClickable(true);
        } else {
            view.animate().alpha(0f).setDuration(400).start();
            view.setClickable(false);
        }
    }
}
