package com.jay.currencyconverter.model

class ResponseWrapper<T>(val response: T? = null, val error: Throwable? = null)