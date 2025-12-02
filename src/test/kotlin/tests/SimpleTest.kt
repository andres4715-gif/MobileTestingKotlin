package tests

import org.testng.annotations.Test
import org.testng.Assert.assertTrue

/**
 * Simple test class to verify project compiles and tests run
 */
class SimpleTest {
    
    @Test
    fun testSimple() {
        println("Running simple test")
        assertTrue(true, "Simple test should pass")
    }
    
    @Test
    fun testEnvironmentVariables() {
        val mockMode = System.getProperty("mock") ?: "false"
        println("Mock mode: $mockMode")
        
        val javaVersion = System.getProperty("java.version")
        println("Java version: $javaVersion")
        
        assertTrue(true, "Environment variables test should pass")
    }
    
    @Test
    fun testFrameworkVersion() {
        println("Framework version: 1.0.0")
        println("Author: BrowserStack Mobile Testing Framework")
        assertTrue(true, "Framework version test should pass")
    }
}
