package com.jay.currencyconverter.model

class ResponseWrapper<T>(val data: T? = null, val error: Throwable? = null)