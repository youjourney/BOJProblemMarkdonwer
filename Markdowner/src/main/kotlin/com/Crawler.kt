package com

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    println("${File("").absolutePath}")

    var fr = FileReader(File("${File("").absolutePath}/Markdowner/src/main/kotlin/com/edgeOptions.txt"))
    var br = BufferedReader(fr)

    val edgeOptions = mutableListOf<String>()
    br.forEachLine { edgeOptions.add(it) }

    fr = FileReader(File("${File("").absolutePath}/Markdowner/src/main/kotlin/com/configPaths.txt"))
    br = BufferedReader(fr)
    val configPaths = mutableListOf<String>()
    br.forEachLine { configPaths.add(it) }

    br.close()
    fr.close()

    val mdmaker: MarkdownMaker = MarkdownMaker(edgeOptions.toTypedArray(), configPaths.toTypedArray(), args[0])
}

