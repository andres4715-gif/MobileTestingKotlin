package utils

import drivers.DriverManager
import io.qameta.allure.Allure
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility object for screenshot operations
 */
object ScreenshotUtils {
    
    private const val SCREENSHOT_DIR = "screenshots"
    
    init {
        // Create screenshots directory if it doesn't exist
        File(SCREENSHOT_DIR).mkdirs()
    }
    
    /**
     * Take screenshot and save to file
     */
    fun takeScreenshot(testName: String): String? {
        return try {
            val driver = DriverManager.getDriver() as TakesScreenshot
            val screenshot = driver.getScreenshotAs(OutputType.FILE)
            
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = "${testName}_${timestamp}.png"
            val destinationPath = Paths.get(SCREENSHOT_DIR, fileName)
            
            Files.copy(screenshot.toPath(), destinationPath)
            
            println("✓ Screenshot saved: $destinationPath")
            destinationPath.toString()
        } catch (e: Exception) {
            println("⚠ Failed to take screenshot: ${e.message}")
            null
        }
    }
    
    /**
     * Take screenshot and attach to Allure report
     */
    fun attachScreenshotToAllure(name: String = "Screenshot") {
        try {
            val driver = DriverManager.getDriver() as TakesScreenshot
            val screenshot = driver.getScreenshotAs(OutputType.BYTES)
            
            Allure.addAttachment(name, "image/png", ByteArrayInputStream(screenshot), "png")
            println("✓ Screenshot attached to Allure report")
        } catch (e: Exception) {
            println("⚠ Failed to attach screenshot to Allure: ${e.message}")
        }
    }
    
    /**
     * Take screenshot on test failure
     */
    fun takeScreenshotOnFailure(testName: String) {
        takeScreenshot("FAILURE_$testName")
        attachScreenshotToAllure("Failure Screenshot - $testName")
    }
}
