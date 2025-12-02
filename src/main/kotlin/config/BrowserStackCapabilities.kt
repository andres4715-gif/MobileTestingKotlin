package config

import org.openqa.selenium.remote.DesiredCapabilities

/**
 * Object to manage BrowserStack capabilities for Android and iOS
 */
object BrowserStackCapabilities {
    
    /**
     * Get capabilities for Android platform
     */
    fun getAndroidCapabilities(): DesiredCapabilities {
        return DesiredCapabilities().apply {
            // BrowserStack specific capabilities
            setCapability("browserstack.user", ConfigManager.browserstackUsername)
            setCapability("browserstack.key", ConfigManager.browserstackAccessKey)
            
            // App configuration
            setCapability("app", ConfigManager.androidAppUrl)
            
            // Device configuration
            setCapability("platformName", "Android")
            setCapability("platformVersion", ConfigManager.osVersion)
            setCapability("deviceName", ConfigManager.deviceName)
            
            // Test configuration
            setCapability("project", ConfigManager.projectName)
            setCapability("build", ConfigManager.buildName)
            setCapability("name", "Android Test")
            
            // BrowserStack features
            setCapability("browserstack.debug", ConfigManager.debug)
            setCapability("browserstack.networkLogs", ConfigManager.networkLogs)
            setCapability("browserstack.video", true)
            setCapability("browserstack.appiumVersion", "2.0.1")
            
            // Appium automation name
            setCapability("automationName", "UiAutomator2")
            
            // Additional settings
            setCapability("autoGrantPermissions", true)
            setCapability("noReset", false)
            setCapability("fullReset", false)
        }
    }
    
    /**
     * Get capabilities for iOS platform
     */
    fun getIOSCapabilities(): DesiredCapabilities {
        return DesiredCapabilities().apply {
            // BrowserStack specific capabilities
            setCapability("browserstack.user", ConfigManager.browserstackUsername)
            setCapability("browserstack.key", ConfigManager.browserstackAccessKey)
            
            // App configuration
            setCapability("app", ConfigManager.iosAppUrl)
            
            // Device configuration
            setCapability("platformName", "iOS")
            setCapability("platformVersion", ConfigManager.osVersion)
            setCapability("deviceName", ConfigManager.deviceName)
            
            // Test configuration
            setCapability("project", ConfigManager.projectName)
            setCapability("build", ConfigManager.buildName)
            setCapability("name", "iOS Test")
            
            // BrowserStack features
            setCapability("browserstack.debug", ConfigManager.debug)
            setCapability("browserstack.networkLogs", ConfigManager.networkLogs)
            setCapability("browserstack.video", true)
            setCapability("browserstack.appiumVersion", "2.0.1")
            
            // Appium automation name
            setCapability("automationName", "XCUITest")
            
            // Additional settings
            setCapability("autoAcceptAlerts", true)
            setCapability("noReset", false)
            setCapability("fullReset", false)
        }
    }
    
    /**
     * Get capabilities based on current platform configuration
     */
    fun getCapabilities(): DesiredCapabilities {
        return when (ConfigManager.platform.lowercase()) {
            "android" -> getAndroidCapabilities()
            "ios" -> getIOSCapabilities()
            else -> throw IllegalArgumentException("Unsupported platform: ${ConfigManager.platform}")
        }
    }
}
