package com.jay.currencyconverter.util.common

import com.jay.currencyconverter.util.common.Constant.SSL_HAND_SHAKE_ABORTED_MSG
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ConnectionErrorHandlerTest {

    private var isErrorHandled = false

    @After
    fun afterEach() {
        isErrorHandled = false
    }

    @Test
    fun onSslHandshakeAborted_correctHandlePassedError() {
        ConnectionErrorHandler.onSslHandshakeAborted(Throwable(SSL_HAND_SHAKE_ABORTED_MSG)) {
            isErrorHandled = true
        }
        assertTrue(isErrorHandled)
    }

    @Test
    fun onSslHandshakeAborted_ignoreHandlingOtherErrors() {
        ConnectionErrorHandler.onSslHandshakeAborted(Throwable("some other error msg")) {
            isErrorHandled = true
        }
        assertFalse(isErrorHandled)
    }

    @Test
    fun onSslHandshakeAborted_nullErrorMessage() {
        val nullMessage: String? = null

        ConnectionErrorHandler.onSslHandshakeAborted(Throwable(nullMessage)) {
            isErrorHandled = true
        }
        assertFalse(isErrorHandled)
    }
}