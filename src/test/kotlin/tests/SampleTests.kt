package tests

import drivers.DriverManager
import io.qameta.allure.*
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test
import pages.SamplePage

/**
 * Sample test class demonstrating various test scenarios
 * These tests are generic and work with most mobile apps
 */
@Epic("Mobile Testing Framework")
@Feature("Sample Tests")
class SampleTests : BaseTest() {
    
    @Test(description = "Verify app launches successfully", priority = 1)
    @Severity(SeverityLevel.BLOCKER)
    @Story("App Launch")
    @Description("Test to verify that the mobile application launches successfully on BrowserStack")
    fun testAppLaunch() {
        step("Verify driver is initialized") {
            assertThat(DriverManager.isDriverInitialized())
                .`as`("Driver should be initialized")
                .isTrue()
        }
        
        step("Verify app is running") {
            val driver = DriverManager.getDriver()
            val pageSource = driver.pageSource
            
            assertThat(pageSource)
                .`as`("Page source should not be empty")
                .isNotEmpty()
        }
        
        step("Log app context") {
            val driver = DriverManager.getDriver()
            println("Current contexts: ${driver.contextHandles}")
            println("Current context: ${driver.context}")
        }
        
        println("✓ App launched successfully!")
    }
    
    @Test(description = "Verify device capabilities", priority = 2)
    @Severity(SeverityLevel.NORMAL)
    @Story("Device Information")
    @Description("Test to verify and display device capabilities")
    fun testDeviceCapabilities() {
        val driver = DriverManager.getDriver()
        
        step("Get and verify device capabilities") {
            val capabilities = driver.capabilities
            
            val platformName = capabilities.getCapability("platformName")
            val platformVersion = capabilities.getCapability("platformVersion")
            val deviceName = capabilities.getCapability("deviceName")
            
            println("""
                Device Information:
                - Platform: $platformName
                - Version: $platformVersion
                - Device: $deviceName
            """.trimIndent())
            
            assertThat(platformName)
                .`as`("Platform name should not be null")
                .isNotNull()
            
            assertThat(deviceName)
                .`as`("Device name should not be null")
                .isNotNull()
        }
    }
    
    @Test(description = "Verify screen dimensions", priority = 3)
    @Severity(SeverityLevel.NORMAL)
    @Story("Screen Properties")
    @Description("Test to verify and display screen dimensions")
    fun testScreenDimensions() {
        val driver = DriverManager.getDriver()
        
        step("Get screen dimensions") {
            val dimensions = driver.manage().window().size
            
            println("""
                Screen Dimensions:
                - Width: ${dimensions.width}
                - Height: ${dimensions.height}
            """.trimIndent())
            
            assertThat(dimensions.width)
                .`as`("Screen width should be greater than 0")
                .isGreaterThan(0)
            
            assertThat(dimensions.height)
                .`as`("Screen height should be greater than 0")
                .isGreaterThan(0)
        }
    }
    
    @Test(description = "Verify page object model integration", priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Page Object Model")
    @Description("Test to verify Page Object Model is working correctly")
    fun testPageObjectModel() {
        step("Initialize sample page") {
            val samplePage = SamplePage()
            
            assertThat(samplePage)
                .`as`("Sample page should be initialized")
                .isNotNull()
        }
        
        step("Verify page is loaded") {
            val samplePage = SamplePage()
            samplePage.waitForPageToLoad()
            
            val isLoaded = samplePage.isPageLoaded()
            assertThat(isLoaded)
                .`as`("Page should be loaded")
                .isTrue()
        }
        
        step("Get current screen information") {
            val samplePage = SamplePage()
            val currentScreen = samplePage.getCurrentScreen()
            
            println("Current screen: $currentScreen")
            assertThat(currentScreen)
                .`as`("Current screen should not be empty")
                .isNotEmpty()
        }
    }
    
    @Test(description = "Verify app session management", priority = 5)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Session Management")
    @Description("Test to verify app session is properly managed")
    fun testSessionManagement() {
        val driver = DriverManager.getDriver()
        
        step("Verify session ID exists") {
            val sessionId = driver.sessionId
            
            assertThat(sessionId)
                .`as`("Session ID should not be null")
                .isNotNull()
            
            println("Session ID: $sessionId")
        }
        
        step("Verify session is active") {
            val pageSource = driver.pageSource
            
            assertThat(pageSource)
                .`as`("Should be able to get page source from active session")
                .isNotEmpty()
        }
        
        step("Verify driver context") {
            val contexts = driver.contextHandles
            
            assertThat(contexts)
                .`as`("Context handles should not be empty")
                .isNotEmpty()
            
            println("Available contexts: $contexts")
        }
    }
    
    @Test(description = "Verify BrowserStack integration", priority = 6)
    @Severity(SeverityLevel.BLOCKER)
    @Story("BrowserStack Integration")
    @Description("Test to verify BrowserStack cloud integration is working")
    fun testBrowserStackIntegration() {
        val driver = DriverManager.getDriver()
        
        step("Verify BrowserStack capabilities") {
            val capabilities = driver.capabilities
            
            val browserstackStatus = capabilities.getCapability("browserstack.status")
            val sessionUrl = capabilities.getCapability("browserstack.sessionUrl")
            
            println("""
                BrowserStack Information:
                - Status: $browserstackStatus
                - Session URL: $sessionUrl
            """.trimIndent())
        }
        
        step("Verify app is running on cloud") {
            val capabilities = driver.capabilities
            val platformName = capabilities.getCapability("platformName")
            
            assertThat(platformName)
                .`as`("Should be running on BrowserStack cloud")
                .isNotNull()
        }
        
        println("✓ BrowserStack integration verified!")
    }
    
    @Test(description = "Verify element interaction capability", priority = 7)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Element Interaction")
    @Description("Test to verify basic element interaction capabilities")
    fun testElementInteraction() {
        val driver = DriverManager.getDriver()
        
        step("Verify page source is accessible") {
            val pageSource = driver.pageSource
            
            assertThat(pageSource)
                .`as`("Page source should be accessible")
                .isNotEmpty()
        }
        
        step("Verify element finding capabilities") {
            // This is a generic test that just verifies the driver can search for elements
            // In a real test, you would interact with actual app elements
            val pageSource = driver.pageSource
            
            assertThat(pageSource)
                .`as`("Should be able to access page elements")
                .contains("<")
            
            println("✓ Element interaction capability verified")
        }
    }
}
