package util

import android.view.View
import androidx.core.view.isVisible
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

object ViewHelper {

    fun waitTillAppear(view: View, onAppear: () -> Unit){
        Observable.interval(10, 100, TimeUnit.SECONDS)
            .subscribe { if (view.isVisible) onAppear() }
    }

}