package com.jay.currencyconverter.util.common

object ConnectionErrorHandler {

    fun onSslHandshakeAborted(error: Throwable, execute: () -> Unit) {
        error.message?.let { errorMessage: String ->
            if (errorMessage.startsWith("SSL handshake aborted", ignoreCase = true)) {
                execute()
            }
        }
    }
}