package com

import org.openqa.selenium.By
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar

class MDFrontMatterMaker {
    private var crawler: EdgeCrawler

    constructor(edgeCrawler: EdgeCrawler) {
        crawler = edgeCrawler
    }

    fun makeMDFrontMatter(): String {
        println("[Markdowner Log] 6. Start making MD front matters")
        val strArr = mutableListOf<String>()

        //begin frontmatters
        strArr.add("---")

        //title
        strArr.add(
            let {
                val title = crawler.edgeDriver
                    .title
                    .split("번:")
                    .map { it.trim() }

                "title: \"[백준] ${title[1]} (${title[0]})(kotlin)\""
            }
        )

        //excerpt
        strArr.add(
            let {
                val description = crawler.edgeDriver
                    .findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div[5]/div[1]/section/div[2]"))
                    .text
                    .split(".")
                    .let {
                        var text = it[0]
                        if (it.size != 1 && it[0] == "") {
                            for (i in text.indices) {
                                if (it[i] == "") continue
                                else {
                                    text = it[i]
                                    break
                                }
                            }
                        }

                        text
                    }
                    .replace("\"", "\\\"")
                    .replace("\n", " ") + "."

                "excerpt: \"$description\""
            }
        )

        //categories
        strArr.add("categories:\n- boj")

        //tags
        strArr.add("tags:\n- algorithm\n- kotlin")

        //last_modified_at
        strArr.add(
            let {
                val currentDateTime: Date = Calendar.getInstance().time
                val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+09:00")

                "last_modified_at: ${dateFormat.format(currentDateTime)}"
            }
        )

        //end of frontmatters
        strArr.add("---")
        return strArr.joinToString("\n", "", "\n")
    }
}