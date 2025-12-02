package utils

import drivers.DriverManager
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
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
     * Save screenshot with specific name
     */
    fun saveScreenshot(name: String = "Screenshot") {
        try {
            val driver = DriverManager.getDriver() as TakesScreenshot
            val screenshot = driver.getScreenshotAs(OutputType.FILE)
            
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = "${name}_${timestamp}.png"
            val destinationPath = Paths.get(SCREENSHOT_DIR, fileName)
            
            Files.copy(screenshot.toPath(), destinationPath)
            println("✓ Screenshot saved to: $destinationPath")
        } catch (e: Exception) {
            println("⚠ Failed to take screenshot: ${e.message}")
        }
    }
    
    /**
     * Take screenshot on test failure
     */
    fun takeScreenshotOnFailure(testName: String) {
        takeScreenshot("FAILURE_$testName")
    }
}
