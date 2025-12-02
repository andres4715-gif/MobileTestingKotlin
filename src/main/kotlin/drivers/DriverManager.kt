package drivers

import config.BrowserStackCapabilities
import config.ConfigManager
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.time.Duration

/**
 * Singleton object to manage Appium driver lifecycle
 * Implements thread-local storage for parallel test execution
 */
object DriverManager {
    
    private val driverThreadLocal = ThreadLocal<AppiumDriver>()
    
    /**
     * Initialize driver based on platform configuration
     */
    fun initializeDriver() {
        val capabilities = BrowserStackCapabilities.getCapabilities()
        val driver = createDriver(capabilities)
        
        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        
        driverThreadLocal.set(driver)
        
        println("✓ Driver initialized successfully for platform: ${ConfigManager.platform}")
    }
    
    /**
     * Create driver based on platform
     */
    private fun createDriver(capabilities: DesiredCapabilities): AppiumDriver {
        val hubUrl = URL(ConfigManager.browserstackHub)
        
        return when (ConfigManager.platform.lowercase()) {
            "android" -> {
                println("Creating Android driver...")
                AndroidDriver(hubUrl, capabilities)
            }
            "ios" -> {
                println("Creating iOS driver...")
                IOSDriver(hubUrl, capabilities)
            }
            else -> throw IllegalArgumentException("Unsupported platform: ${ConfigManager.platform}")
        }
    }
    
    /**
     * Get current driver instance
     */
    fun getDriver(): AppiumDriver {
        return driverThreadLocal.get()
            ?: throw IllegalStateException("Driver not initialized. Call initializeDriver() first.")
    }
    
    /**
     * Quit driver and clean up
     */
    fun quitDriver() {
        try {
            driverThreadLocal.get()?.let { driver ->
                driver.quit()
                println("✓ Driver quit successfully")
            }
        } catch (e: Exception) {
            println("⚠ Error quitting driver: ${e.message}")
        } finally {
            driverThreadLocal.remove()
        }
    }
    
    /**
     * Check if driver is initialized
     */
    fun isDriverInitialized(): Boolean {
        return try {
            driverThreadLocal.get() != null
        } catch (e: Exception) {
            false
        }
    }
}
