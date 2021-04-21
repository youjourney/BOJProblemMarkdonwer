package com

import org.openqa.selenium.By
import org.openqa.selenium.WebDriverException
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class MDBodyMaker {
    private lateinit var crawler: EdgeCrawler
    private lateinit var problem: String

    constructor(crawler: EdgeCrawler, pn: String) {
        this.crawler = crawler
        this.problem = pn

    }

    fun makeProblemDescriptions(): String {
        println("[Markdowner Log] 7. Start making the MD body of problem descriptions")
        val strArr = mutableListOf<String>()

        fun br3() = strArr.add("\n\n\n")
        fun br2() = strArr.add("\n\n")
        fun br1() = strArr.add("\n")

        //문제 설명
        br1()
        strArr.add("### 문제 설명")
        strArr.add("[백준 ${problem}번 문제 링크](https://www.acmicpc.net/problem/${problem}#description){:target=\"_blank\"}")
        br3()

        //문제 입출력 설명
        val problem_input = crawler.edgeDriver.findElement(By.id("problem_input"))
            .findElements(By.tagName("p"))
            .map {
                it
                    .text
                    .replace("\n", "")
                    .replace("-", "\\-")
            }
        val problem_output = crawler.edgeDriver.findElement(By.id("problem_output"))
            .findElements(By.tagName("p"))
            .map {
                it
                    .text
                    .replace("\n", "")
                    .replace("-", "\\-")
            }
        strArr.add("### 입력 및 출력")
        if (problem_input[0] != "") {
            strArr.add("#### >> 입력")
            if (problem_input.size == 1) {
                strArr.addAll(problem_input)
            } else {
                strArr.add(problem_input.joinToString("\n", "* "))
            }
            br2()
        }
        if (problem_output[0] != "") {
            strArr.add("#### >> 출력")
            if (problem_output.size == 1) {
                strArr.addAll(problem_output)
            } else {
                strArr.add(problem_output.joinToString("\n", "* "))
            }
            br2()
        }
        br1()

        //문제 제한 설명
        val problem_limit = crawler.edgeDriver.findElement(By.id("problem_limit")).text
        if (problem_limit != "") {
            strArr.add("### 제한 사항")
            br1()
            strArr.add(problem_limit.replace("-", "\\-"))
            br3()
        }

        //문제 예제 입출력
        var sample_input_count = 1
        while (true) {
            try {
                crawler.edgeDriver.findElement(By.id("sample-input-${sample_input_count}"))
                sample_input_count++
            } catch (e: WebDriverException) {
                sample_input_count--
                break
            }
        }

        if (sample_input_count != 0) {
            strArr.add("### 예제 입출력(테스트케이스)")
            br1()
            strArr.add("|입력|출력|")
            strArr.add("|-----|------|")
            for (count in 1..sample_input_count) {
                val input = crawler.edgeDriver.findElement(By.id("sample-input-$count"))
                    .text
                    .let {
                        it.replace("\n", "<br>")
                    }
                    .replace("-", "\\-")
                val output = crawler.edgeDriver.findElement(By.id("sample-output-$count"))
                    .text
                    .let {
                        it.replace("\n", "<br>")
                    }
                    .replace("-", "\\-")
                strArr.add("|$input|$output|")
            }
            br3()
        }

        //문제 힌트
        val problem_hint = crawler.edgeDriver.findElement(By.id("problem_hint")).text
        if (problem_hint != "") {
            strArr.add("### 문제 힌트")
            br1()
            strArr.add(problem_hint.replace("-", "\\-"))
            br3()
        }

        return strArr.joinToString("\n", "", "\n")
    }

    fun loadKtSource() : String {
        println("[Markdowner Log] 8. Start making the MD body of the problem source.")
        val str = mutableListOf<String>()
        val path = "/Users/choiyoujun/Documents/GIT/ProblemSolutions/BOJ${problem}/src/com.main/kotlin/com.main.kt"
        //val path = "/Users/사용자폴더/Documents/GIT_ProblemSolutions/ProblemSolutions/BOJ${problem}/src/com.main/kotlin/com.main.kt"

        try {
            val file = File(path)
            val fr = FileReader(file)
            val br = BufferedReader(fr)

            str.add("### 문제 풀이1")
            str.add("```kotlin")
            str.addAll(br.readLines())
            str.add("```")

            br.close()
            fr.close()
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalArgumentException("ERROR -> Problem source file does not exist.")
        }

        return str.joinToString("\n")
    }

}