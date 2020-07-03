package com.jay.currencyconverter.util.common

import com.jay.currencyconverter.util.common.Constant.SSL_HAND_SHAKE_ABORTED_MSG

object ConnectionErrorHandler {

    fun onSslHandshakeAborted(error: Throwable, execute: () -> Unit) {
        error.message?.let { errorMessage: String ->
            if (errorMessage.startsWith(SSL_HAND_SHAKE_ABORTED_MSG, ignoreCase = true)) {
                execute()
            }
        }
    }
}