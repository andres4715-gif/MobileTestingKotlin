package config

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

/**
 * Singleton object to manage configuration from environment variables
 */
object ConfigManager {
    
    private val dotenv: Dotenv = dotenv {
        ignoreIfMissing = true
    }
    
    // BrowserStack Credentials
    val browserstackUsername: String
        get() = getEnvVariable("BROWSERSTACK_USERNAME")
    
    val browserstackAccessKey: String
        get() = getEnvVariable("BROWSERSTACK_ACCESS_KEY")
    
    // App URLs
    val androidAppUrl: String
        get() = getEnvVariable("ANDROID_APP_URL")
    
    val iosAppUrl: String
        get() = getEnvVariable("IOS_APP_URL")
    
    // Platform Configuration
    val platform: String
        get() = getEnvVariable("PLATFORM", "android")
    
    val deviceName: String
        get() = getEnvVariable("DEVICE_NAME", "Google Pixel 7")
    
    val osVersion: String
        get() = getEnvVariable("OS_VERSION", "13.0")
    
    // BrowserStack Configuration
    val projectName: String
        get() = getEnvVariable("BROWSERSTACK_PROJECT_NAME", "Mobile Testing Framework")
    
    val buildName: String
        get() = getEnvVariable("BROWSERSTACK_BUILD_NAME", "Build_${System.currentTimeMillis()}")
    
    val debug: Boolean
        get() = getEnvVariable("BROWSERSTACK_DEBUG", "true").toBoolean()
    
    val networkLogs: Boolean
        get() = getEnvVariable("BROWSERSTACK_NETWORK_LOGS", "true").toBoolean()
    
    // BrowserStack Hub URL
    val browserstackHub: String
        get() = "https://$browserstackUsername:$browserstackAccessKey@hub-cloud.browserstack.com/wd/hub"
    
    /**
     * Get environment variable from .env file or system environment
     */
    private fun getEnvVariable(key: String, defaultValue: String = ""): String {
        return System.getenv(key) 
            ?: dotenv[key] 
            ?: defaultValue.also {
                if (it.isEmpty() && isRequiredKey(key)) {
                    throw IllegalStateException("Required environment variable '$key' is not set")
                }
            }
    }
    
    /**
     * Check if the key is required
     */
    private fun isRequiredKey(key: String): Boolean {
        return key in listOf("BROWSERSTACK_USERNAME", "BROWSERSTACK_ACCESS_KEY")
    }
    
    /**
     * Get app URL based on platform
     */
    fun getAppUrl(): String {
        return when (platform.lowercase()) {
            "android" -> androidAppUrl
            "ios" -> iosAppUrl
            else -> throw IllegalArgumentException("Invalid platform: $platform. Must be 'android' or 'ios'")
        }
    }
    
    /**
     * Validate configuration
     */
    fun validateConfig() {
        require(browserstackUsername.isNotEmpty()) { "BrowserStack username is required" }
        require(browserstackAccessKey.isNotEmpty()) { "BrowserStack access key is required" }
        require(platform.lowercase() in listOf("android", "ios")) { "Platform must be 'android' or 'ios'" }
        
        println("""
            ╔════════════════════════════════════════════════════════════╗
            ║           Configuration Loaded Successfully                ║
            ╠════════════════════════════════════════════════════════════╣
            ║ Platform: $platform
            ║ Device: $deviceName
            ║ OS Version: $osVersion
            ║ Project: $projectName
            ║ Build: $buildName
            ╚════════════════════════════════════════════════════════════╝
        """.trimIndent())
    }
}
