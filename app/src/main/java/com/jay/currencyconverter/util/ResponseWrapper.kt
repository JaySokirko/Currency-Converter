package com.jay.currencyconverter.util

class ResponseWrapper<T>(val data: T? = null, val error: Throwable? = null)