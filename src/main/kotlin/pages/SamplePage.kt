package pages

import io.appium.java_client.AppiumBy
import org.openqa.selenium.By

/**
 * Sample Page Object - Replace with your actual app pages
 * This is a generic example that works with many sample apps
 */
class SamplePage : BasePage() {
    
    // Example locators - Update these based on your actual app
    private val searchButton: By = AppiumBy.accessibilityId("Search")
    private val searchInput: By = By.id("search_input")
    private val resultText: By = By.id("result_text")
    
    // Generic text locator for demos
    private fun textLocator(text: String): By = 
        if (driver.capabilities.getCapability("platformName").toString().lowercase() == "android") {
            By.xpath("//*[@text='$text']")
        } else {
            By.xpath("//*[@name='$text' or @label='$text']")
        }
    
    /**
     * Wait for page to load
     */
    override fun waitForPageToLoad() {
        // Wait for any element that indicates the page is loaded
        // Update this based on your app
        Thread.sleep(3000) // Temporary wait for demo
    }
    
    /**
     * Check if page is loaded
     */
    override fun isPageLoaded(): Boolean {
        return try {
            driver.currentActivity != null || driver.title != null
            true
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Click on element with text
     */
    fun clickElementWithText(text: String) {
        click(textLocator(text))
    }
    
    /**
     * Verify text is displayed
     */
    fun isTextDisplayed(text: String): Boolean {
        return isDisplayed(textLocator(text))
    }
    
    /**
     * Get current activity (Android) or page source
     */
    fun getCurrentScreen(): String {
        return try {
            driver.currentActivity ?: driver.pageSource
        } catch (e: Exception) {
            driver.pageSource
        }
    }
    
    /**
     * Tap at coordinates
     */
    fun tapAtCoordinates(x: Int, y: Int) {
        // Implementation for tap at specific coordinates
        println("Tapping at coordinates: ($x, $y)")
    }
}
