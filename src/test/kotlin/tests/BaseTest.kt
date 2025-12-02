package tests

import config.ConfigManager
import drivers.DriverManager
import io.qameta.allure.Allure
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeMethod
import org.testng.annotations.BeforeSuite
import utils.ScreenshotUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Base Test class that all test classes should extend
 * Handles driver initialization, teardown, and test lifecycle
 */
abstract class BaseTest {
    
    companion object {
        private var suiteStartTime: Long = 0
    }
    
    @BeforeSuite(alwaysRun = true)
    fun setupSuite() {
        suiteStartTime = System.currentTimeMillis()
        println("""
            ╔════════════════════════════════════════════════════════════╗
            ║              MOBILE TESTING FRAMEWORK                      ║
            ║              Starting Test Suite                           ║
            ╚════════════════════════════════════════════════════════════╝
        """.trimIndent())
        
        // Validate configuration
        ConfigManager.validateConfig()
    }
    
    @BeforeMethod(alwaysRun = true)
    fun setupTest(result: ITestResult) {
        val testName = result.method.methodName
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        
        println("""
            
            ┌────────────────────────────────────────────────────────────┐
            │ Starting Test: $testName
            │ Time: $timestamp
            │ Platform: ${ConfigManager.platform}
            └────────────────────────────────────────────────────────────┘
        """.trimIndent())
        
        try {
            // Initialize driver
            DriverManager.initializeDriver()
            
            // Add test info to Allure
            Allure.parameter("Platform", ConfigManager.platform)
            Allure.parameter("Device", ConfigManager.deviceName)
            Allure.parameter("OS Version", ConfigManager.osVersion)
            Allure.parameter("Build", ConfigManager.buildName)
            
        } catch (e: Exception) {
            println("⚠ Error setting up test: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }
    
    @AfterMethod(alwaysRun = true)
    fun teardownTest(result: ITestResult) {
        val testName = result.method.methodName
        val status = if (result.isSuccess) "✓ PASSED" else "✗ FAILED"
        
        try {
            // Take screenshot if test failed
            if (!result.isSuccess) {
                println("⚠ Test failed, capturing screenshot...")
                ScreenshotUtils.takeScreenshotOnFailure(testName)
                
                // Add failure details to Allure
                if (result.throwable != null) {
                    Allure.addAttachment("Failure Details", result.throwable.toString())
                }
            } else {
                // Optionally take screenshot on success as well
                ScreenshotUtils.attachScreenshotToAllure("Test Success - $testName")
            }
            
        } catch (e: Exception) {
            println("⚠ Error during test cleanup: ${e.message}")
        } finally {
            // Quit driver
            DriverManager.quitDriver()
            
            println("""
            ┌────────────────────────────────────────────────────────────┐
            │ Test Completed: $testName
            │ Status: $status
            └────────────────────────────────────────────────────────────┘
            
            """.trimIndent())
        }
    }
    
    @AfterSuite(alwaysRun = true)
    fun teardownSuite() {
        val duration = (System.currentTimeMillis() - suiteStartTime) / 1000
        val minutes = duration / 60
        val seconds = duration % 60
        
        println("""
            ╔════════════════════════════════════════════════════════════╗
            ║              Test Suite Completed                          ║
            ║              Duration: ${minutes}m ${seconds}s                           ║
            ╚════════════════════════════════════════════════════════════╝
        """.trimIndent())
    }
    
    /**
     * Helper method to add step to Allure report
     */
    protected fun step(description: String, action: () -> Unit) {
        Allure.step(description) {
            println("→ $description")
            action()
        }
    }
}
