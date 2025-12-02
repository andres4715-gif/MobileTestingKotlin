package pages

import drivers.DriverManager
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import utils.ElementUtils
import utils.WaitUtils
import java.time.Duration

/**
 * Base Page Object class with common page operations
 * All page objects should extend this class
 */
abstract class BasePage {
    
    protected val driver: AppiumDriver = DriverManager.getDriver()
    
    init {
        PageFactory.initElements(driver, this)
    }
    
    /**
     * Click element
     */
    protected fun click(by: By) {
        ElementUtils.clickElement(by)
    }
    
    /**
     * Enter text
     */
    protected fun type(by: By, text: String) {
        ElementUtils.enterText(by, text)
    }
    
    /**
     * Get text from element
     */
    protected fun getText(by: By): String {
        return ElementUtils.getText(by)
    }
    
    /**
     * Check if element is displayed
     */
    protected fun isDisplayed(by: By): Boolean {
        return ElementUtils.isElementDisplayed(by)
    }
    
    /**
     * Wait for element to be visible
     */
    protected fun waitForVisible(by: By, timeout: Long = 30): WebElement {
        return WaitUtils.waitForElementVisible(by, timeout)
    }
    
    /**
     * Wait for element to be clickable
     */
    protected fun waitForClickable(by: By, timeout: Long = 30): WebElement {
        return WaitUtils.waitForElementClickable(by, timeout)
    }
    
    /**
     * Scroll to element
     */
    protected fun scrollTo(by: By) {
        ElementUtils.scrollToElement(by)
    }
    
    /**
     * Find elements
     */
    protected fun findElements(by: By): List<WebElement> {
        return ElementUtils.findElements(by)
    }
    
    /**
     * Get attribute value
     */
    protected fun getAttribute(by: By, attribute: String): String? {
        return ElementUtils.getAttribute(by, attribute)
    }
    
    /**
     * Wait for page to load (override in specific pages)
     */
    abstract fun waitForPageToLoad()
    
    /**
     * Verify page is loaded (override in specific pages)
     */
    abstract fun isPageLoaded(): Boolean
}
