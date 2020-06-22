package com.jay.currencyconverter.util.ui

import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import java.util.concurrent.TimeUnit

class HintManager {

    fun showHint(targetView: View?, title: String, onComplete: (() -> Unit)? = null) {
        targetView?.let {
            GuideView.Builder(it.context)
                    .setTitle(title)
                    .setTargetView(it)
                    .setDismissType(DismissType.outside)
                .setGuideListener { onComplete?.let { onComplete() } }
                    .build()
                    .show()
        }
    }

    fun showDelayedHint(delay: Long = 1500,
                        targetView: View?,
                        title: String,
                        onComplete: (() -> Unit)? = null): Disposable {

        return Observable.timer(delay, TimeUnit.MILLISECONDS)
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe {
                    targetView?.let {
                        GuideView.Builder(it.context)
                                .setTitle(title)
                                .setTargetView(it)
                                .setDismissType(DismissType.outside)
                                .setGuideListener { onComplete?.let { onComplete() } }
                                .build()
                                .show()
                    }
                }
    }
}