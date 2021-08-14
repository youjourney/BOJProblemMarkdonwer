package com.youjourney

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
//    println("${File("").absolutePath}")
//    println("${ClassLoader.getSystemClassLoader().getResource(".").getPath()}")

    val edgeOptions = mutableListOf<String>()
    File("/Users/choiyoujun/Documents/GIT/BOJProblemMarkdonwer/Markdowner/build/libs/edgeOptions.txt")
        .useLines { lines ->
            lines.forEach { edgeOptions.add(it) }
        }

    val configPaths = mutableListOf<String>()
    File("/Users/choiyoujun/Documents/GIT/BOJProblemMarkdonwer/Markdowner/build/libs/configPaths.txt")
        .useLines { lines ->
            lines.forEach { configPaths.add(it) }
        }

    MarkdownMaker(edgeOptions.toTypedArray(), configPaths.toTypedArray(), args[0])
}