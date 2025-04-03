package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ClassementApi

fun main(args: Array<String>) {
    runApplication<ClassementApi>(*args)
}