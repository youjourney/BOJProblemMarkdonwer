package com.youjourney

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
        crawler = EdgeCrawler(edgeOptions,  configPaths)

        if (pn == "") {
            throw Exception("Markdown Maker did not receive the problem number as input.")
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
            e.printStackTrace()
            throw Exception("ERROR -> Failed to load the Edge driver...")
        }

        println("[Markdowner Log] 5. Writing the MD at your specified path.")
        makeMDFile()

        driverCloser()
    }

    private fun makeMDFile() {
        val currentDateTime: Date = Calendar.getInstance().time
        val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val path = "/Users/choiyoujun/Documents/GIT/youjourney.github.io/_posts/"
        val fileName = "${dateFormat.format(currentDateTime)}-BOJ${problemNumber}.md"
        val fw = FileWriter(path + fileName)
        val bw = BufferedWriter(fw)

        try {
            bw.write(mdfmm.makeMDFrontMatter() + mdbm.makeProblemDescriptions() + mdbm.loadKtSource() + mdbm.loadSwiftSource())
            println("[Markdowner Log] 9. $fileName is made at specific path you configured successfully.")
        } catch (e: Exception) {
            e.printStackTrace()
            println("[Markdowner Log] 9. ${dateFormat.format(currentDateTime)}-BOJ${problemNumber}.md IS NOT CREATED PROPERLY.")
        } finally {
            bw.close()
            fw.close()
        }

    }

    private fun driverCloser() {
        try {
            crawler.edgeDriver.quit()
            println("[Markdowner Log] 10. Selenium(Edge Driver) is closed.")
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalArgumentException("[Markdowner Log] 10. The Selenium(Edge Driver) IS NOT CLOSED SUCCESSFULLY.")
        }
    }

}