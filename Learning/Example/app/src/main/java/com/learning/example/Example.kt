package com.learning.example

class Example {
}
class Circle (val radius: Double) {
    constructor(name: String) : this(1.0)
    constructor(diameter: Int): this(diameter*1.0 / 2) {
        println("In diameter constructor")
    }
    init {
        println("Area: ${Math.PI * radius * radius}")
    }
}

fun main() {
    val c = Circle(2)
}