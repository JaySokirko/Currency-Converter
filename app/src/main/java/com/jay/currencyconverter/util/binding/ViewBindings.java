package com.jay.currencyconverter.util.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class ViewBindings {

    private static final int textIncreaseDuration = 400;
    private static final int textReduceDuration = 300;

    @BindingAdapter("isVisibleAnimated")
    public static void setIsVisible(View view, boolean isVisible) {
        if (isVisible) {
            view.animate()
                    .alpha(1.0f)
                    .setDuration(textIncreaseDuration)
                    .start();
            view.setClickable(true);
        } else {
            view.animate()
                    .alpha(0f)
                    .setDuration(textReduceDuration)
                    .start();
            view.setClickable(false);
        }
    }
}
