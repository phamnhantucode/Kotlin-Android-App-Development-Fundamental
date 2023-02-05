package com.learning.example

class Aquarium {
    var width = 20
    var height = 40
    var length = 100

    fun printSize() {
        println("----------")
        println("Width: $width cm \nLength: $length cm \nHeight: $height cm")
    }
}

fun buildAquarium() {
    val myAquarium = Aquarium()
    myAquarium.printSize()
    myAquarium.height = 60
    myAquarium.printSize()
}


fun main() {
    buildAquarium()
}