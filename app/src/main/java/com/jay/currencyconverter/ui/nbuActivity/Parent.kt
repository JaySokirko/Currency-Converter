package com.jay.currencyconverter.ui.nbuActivity

class Parent {
    private var name: String? = null
    private var age = 0

    
    constructor(name: String?) {
        this.name = name
    }

    constructor(age: Int) {
        this.age = age
    }

    constructor(name: String?, age: Int) {
        this.name = name
        this.age = age
    }
}