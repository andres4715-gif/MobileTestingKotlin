package utils

import drivers.DriverManager
import io.appium.java_client.AppiumBy
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Pause
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.interactions.Sequence
import java.time.Duration

/**
 * Utility object for common element operations
 */
object ElementUtils {
    
    /**
     * Click element with wait
     */
    fun clickElement(by: By) {
        val element = WaitUtils.waitForElementClickable(by)
        element.click()
        println("✓ Clicked element: $by")
    }
    
    /**
     * Enter text into element
     */
    fun enterText(by: By, text: String, clearFirst: Boolean = true) {
        val element = WaitUtils.waitForElementVisible(by)
        if (clearFirst) {
            element.clear()
        }
        element.sendKeys(text)
        println("✓ Entered text '$text' into element: $by")
    }
    
    /**
     * Get text from element
     */
    fun getText(by: By): String {
        val element = WaitUtils.waitForElementVisible(by)
        val text = element.text
        println("✓ Got text '$text' from element: $by")
        return text
    }
    
    /**
     * Check if element is displayed
     */
    fun isElementDisplayed(by: By): Boolean {
        return try {
            val element = DriverManager.getDriver().findElement(by)
            element.isDisplayed.also {
                println("✓ Element displayed status: $it for $by")
            }
        } catch (e: Exception) {
            println("✓ Element not displayed: $by")
            false
        }
    }
    
    /**
     * Check if element is enabled
     */
    fun isElementEnabled(by: By): Boolean {
        return try {
            val element = WaitUtils.waitForElementVisible(by)
            element.isEnabled.also {
                println("✓ Element enabled status: $it for $by")
            }
        } catch (e: Exception) {
            println("⚠ Element not found or not enabled: $by")
            false
        }
    }
    
    /**
     * Get attribute value
     */
    fun getAttribute(by: By, attribute: String): String? {
        val element = WaitUtils.waitForElementVisible(by)
        return element.getAttribute(attribute)
    }
    
    /**
     * Find elements (multiple)
     */
    fun findElements(by: By): List<WebElement> {
        WaitUtils.waitForElementPresent(by)
        return DriverManager.getDriver().findElements(by)
    }
    
    /**
     * Scroll to element (works for both Android and iOS)
     */
    fun scrollToElement(by: By) {
        try {
            val element = DriverManager.getDriver().findElement(by)
            
            val driver = DriverManager.getDriver()
            val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")
            val sequence = Sequence(finger, 0)
            
            // Get screen dimensions
            val dimensions = driver.manage().window().size
            val startY = (dimensions.height * 0.8).toInt()
            val endY = (dimensions.height * 0.2).toInt()
            val centerX = dimensions.width / 2
            
            sequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY))
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
            sequence.addAction(Pause(finger, Duration.ofMillis(200)))
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), centerX, endY))
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
            
            driver.perform(listOf(sequence))
            println("✓ Scrolled to element: $by")
        } catch (e: Exception) {
            println("⚠ Could not scroll to element: ${e.message}")
        }
    }
    
    /**
     * Find element by accessibility ID
     */
    fun findByAccessibilityId(id: String): By {
        return AppiumBy.accessibilityId(id)
    }
    
    /**
     * Find element by XPath
     */
    fun findByXPath(xpath: String): By {
        return By.xpath(xpath)
    }
    
    /**
     * Find element by ID
     */
    fun findById(id: String): By {
        return By.id(id)
    }
}
