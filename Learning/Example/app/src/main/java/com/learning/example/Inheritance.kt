package com.learning.example

open class Person(age: Int, name: String) {
    init {
        println("My name is $name.")
        println("My age is $age")
    }
}

open class Teacher(age: Int, name: String, open var subject: String): Person(age, name) {

    open fun subject() {
        println("I teach $subject.")
    }
}

class Footballer(age: Int, name: String): Person(age, name) {
    fun playFootball() {
        println("I play for LA Galaxy.")
    }
}

class Lecture(var age: Int, var name: String, override var subject: String, var department: String): Teacher(age, name, subject) {
    override fun subject() {
        println("I teach $subject at university")
    }

    fun showInfo() {
        println("------------------------")
        println("Name: ${this.name}\nAge: $age\nSubject: $subject\nDepartment: $department")
    }
}

fun main(args: Array<String>) {
    val lecture = Lecture(29, "Teacher 1", "Android Programing", "Computer Science")
    lecture.subject()
    lecture.showInfo()
}