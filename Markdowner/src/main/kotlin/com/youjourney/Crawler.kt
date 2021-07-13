package com.youjourney

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
//    println("${File("").absolutePath}")
//    println("${ClassLoader.getSystemClassLoader().getResource(".").getPath()}")

    var fr = FileReader(File("/Users/choiyoujun/Documents/GIT/BOJProblemMarkdonwer/Markdowner/build/libs/configPaths.txt"))
    var br = BufferedReader(fr)

    val edgeOptions = mutableListOf<String>()
    br.forEachLine { edgeOptions.add(it) }

    fr = FileReader(File("/Users/choiyoujun/Documents/GIT/BOJProblemMarkdonwer/Markdowner/build/libs/configPaths.txt"))
    br = BufferedReader(fr)
    val configPaths = mutableListOf<String>()
    br.forEachLine { configPaths.add(it) }

    br.close()
    fr.close()

    MarkdownMaker(edgeOptions.toTypedArray(), configPaths.toTypedArray(), args[0])
}