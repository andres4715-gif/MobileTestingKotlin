package tests

import config.ConfigManager
import drivers.DriverManager
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
            // En entorno de desarrollo o CI, iniciamos un driver real
            if (System.getenv("CI") != "true" && System.getProperty("mock") != "true") {
                // Initialize driver
                DriverManager.initializeDriver()
                
                // Add test info as console logs
                println("Test Info:")
                println("- Platform: ${ConfigManager.platform}")
                println("- Device: ${ConfigManager.deviceName}")
                println("- OS Version: ${ConfigManager.osVersion}")
                println("- Build: ${ConfigManager.buildName}")
            } else {
                println("💡 Running in mock mode - no real driver will be initialized")
            }
        } catch (e: Exception) {
            println("⚠ Error setting up test: ${e.message}")
            e.printStackTrace()
            // No lanzamos la excepción para permitir que los tests corran en modo simulación
        }
    }
    
    @AfterMethod(alwaysRun = true)
    fun teardownTest(result: ITestResult) {
        val testName = result.method.methodName
        val status = if (result.isSuccess) "✓ PASSED" else "✗ FAILED"
        
        // Solo si no estamos en modo mock
        if (System.getenv("CI") != "true" && System.getProperty("mock") != "true") {
            try {
                // Take screenshot if test failed
                if (!result.isSuccess) {
                    println("⚠ Test failed, capturing screenshot...")
                    try {
                        ScreenshotUtils.takeScreenshotOnFailure(testName)
                    } catch (e: Exception) {
                        println("⚠ Error capturing screenshot: ${e.message}")
                    }
                    
                    // Log failure details
                    if (result.throwable != null) {
                        println("⚠ Failure Details: ${result.throwable}")
                    }
                } else {
                    // Optionally take screenshot on success as well
                    try {
                        ScreenshotUtils.saveScreenshot("Success_$testName")
                    } catch (e: Exception) {
                        println("⚠ Error capturing success screenshot: ${e.message}")
                    }
                }
                
            } catch (e: Exception) {
                println("⚠ Error during test cleanup: ${e.message}")
            } finally {
                // Quit driver
                try {
                    if (DriverManager.isDriverInitialized()) {
                        DriverManager.quitDriver()
                    }
                } catch (e: Exception) {
                    println("⚠ Error quitting driver: ${e.message}")
                }
            }
        } else {
            println("💡 Mock mode - skipping screenshots and driver teardown")
        }
        
        println("""
        ┌────────────────────────────────────────────────────────────┐
        │ Test Completed: $testName
        │ Status: $status
        └────────────────────────────────────────────────────────────┘
        
        """.trimIndent())
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
     * Helper method for test steps
     */
    protected fun step(description: String, action: () -> Unit) {
        println("→ $description")
        action()
    }
}
