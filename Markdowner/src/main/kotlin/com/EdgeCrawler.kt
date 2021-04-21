package com

import org.openqa.selenium.By
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions

class EdgeCrawler {
    var webDriverID: String = "webdriver.edge.driver"
    var edgeOption: EdgeOptions = EdgeOptions()
    val edgeDriver: EdgeDriver by lazy { EdgeDriver(edgeOption) }

    constructor(edgeOptions: Array<String>, configPaths: Array<String>) {
        println("[Markdonwer Log] 2. Selenium Edge Crawler initializing...\n")

        // configPaths[0] -> webDriverPath
        System.setProperty(webDriverID, configPaths[0])

        // 엣지 옵션 적용
        println("[Markdonwer Log] 3. Adapting Edge Options to Selenium Edge Crawler...")
        try {
            repeat(edgeOptions.size) {
                edgeOption.addArguments(edgeOptions[it])
            }
        } catch (e: Exception) {
            println("[Markdonwer Log] ERROR_CODE_1 Failed Adapting Edge Options.")
            println(e)
        }

    }
}