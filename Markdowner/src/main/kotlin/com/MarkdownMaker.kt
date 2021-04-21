package com

import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MarkdownMaker {
    private var crawler: EdgeCrawler
    private var problemNumber: String
    private val url: String = "https://www.acmicpc.net/problem/"
    private lateinit var mdfmm: MDFrontMatterMaker
    private lateinit var mdbm: MDBodyMaker

    constructor(edgeOptions: Array<String>, configPaths: Array<String>, pn: String = "") {
        println("[Markdowner Log] 1. initializing Markdown Maker...")
        crawler = EdgeCrawler(edgeOptions, configPaths)

        if (pn == "") {
            println("[Markdowner Log] ERROR_CODE_2 Markdown Maker didn't get Problem Number.")
            throw IOException()
        }
        else problemNumber = pn

        getMarkdown()
    }

    private fun getMarkdown() {
        try {
            println("[Markdowner Log] 4. Loading a Edge driver...")
            crawler.edgeDriver.get(url + problemNumber)
            mdfmm = MDFrontMatterMaker(crawler)
            mdbm = MDBodyMaker(crawler, problemNumber)
        } catch (e: Exception) {
            println("[Markdowner Log] ERROR_CODE_3 Failed to load the Edge driver...")
            println(e)
        }

        println("[Markdowner Log] 8. Writing the MD at your specified path.")
        makeMDFile()

    }

    private fun makeMDFile() {
        val currentDateTime: Date = Calendar.getInstance().time
        val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val path = "/Users/choiyoujun/Documents/GIT/youjourney.github.io/_posts/"
        val fileName = "${dateFormat.format(currentDateTime)}-BOJ${problemNumber}.md"
        val fw = FileWriter(path + fileName)
        val bw = BufferedWriter(fw)

        try {
            bw.write(mdfmm.makeMDFrontMatter() + mdbm.makeProblemDescriptions() + mdbm.loadKtSource())
        } catch (e: Exception) {
            println("Can't write the ${dateFormat.format(currentDateTime)}-${problemNumber}.md")
            e.printStackTrace()
        } finally {
            println("$fileName is made successfully!")
            bw.close()
            fw.close()
        }
    }

}