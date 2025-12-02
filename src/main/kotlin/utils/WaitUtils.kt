package utils

import drivers.DriverManager
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

/**
 * Utility object for wait operations
 */
object WaitUtils {
    
    private const val DEFAULT_TIMEOUT_SECONDS = 30L
    
    /**
     * Wait for element to be visible
     */
    fun waitForElementVisible(by: By, timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS): WebElement {
        val wait = WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds))
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by))
    }
    
    /**
     * Wait for element to be clickable
     */
    fun waitForElementClickable(by: By, timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS): WebElement {
        val wait = WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds))
        return wait.until(ExpectedConditions.elementToBeClickable(by))
    }
    
    /**
     * Wait for element to be present
     */
    fun waitForElementPresent(by: By, timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS): WebElement {
        val wait = WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds))
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }
    
    /**
     * Wait for element to be invisible
     */
    fun waitForElementInvisible(by: By, timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS): Boolean {
        val wait = WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds))
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }
    
    /**
     * Wait for text to be present in element
     */
    fun waitForTextInElement(by: By, text: String, timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS): Boolean {
        val wait = WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds))
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text))
    }
    
    /**
     * Custom wait with condition
     */
    fun <T> waitUntil(condition: () -> T, timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS): T {
        val wait = WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds))
        return wait.until { condition() }
    }
    
    /**
     * Hard wait (use sparingly)
     */
    fun hardWait(milliseconds: Long) {
        Thread.sleep(milliseconds)
    }
}
