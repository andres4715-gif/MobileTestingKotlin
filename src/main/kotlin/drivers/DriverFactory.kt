package drivers

import config.ConfigManager
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver

/**
 * Factory class to provide platform-specific driver operations
 */
object DriverFactory {
    
    /**
     * Get Android driver instance
     */
    fun getAndroidDriver(): AndroidDriver {
        val driver = DriverManager.getDriver()
        require(driver is AndroidDriver) { 
            "Current driver is not an Android driver. Platform: ${ConfigManager.platform}" 
        }
        return driver
    }
    
    /**
     * Get iOS driver instance
     */
    fun getIOSDriver(): IOSDriver {
        val driver = DriverManager.getDriver()
        require(driver is IOSDriver) { 
            "Current driver is not an iOS driver. Platform: ${ConfigManager.platform}" 
        }
        return driver
    }
    
    /**
     * Get generic Appium driver
     */
    fun getDriver(): AppiumDriver {
        return DriverManager.getDriver()
    }
    
    /**
     * Check if current platform is Android
     */
    fun isAndroid(): Boolean {
        return ConfigManager.platform.lowercase() == "android"
    }
    
    /**
     * Check if current platform is iOS
     */
    fun isIOS(): Boolean {
        return ConfigManager.platform.lowercase() == "ios"
    }
}
