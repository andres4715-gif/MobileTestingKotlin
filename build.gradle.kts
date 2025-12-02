import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.qameta.allure") version "2.11.2"
}

group = "com.mobile.testing"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))
    
    // Appium
    implementation("io.appium:java-client:9.1.0")
    
    // Selenium
    implementation("org.seleniumhq.selenium:selenium-java:4.16.1")
    
    // TestNG
    testImplementation("org.testng:testng:7.8.0")
    
    // Allure Reports
    implementation("io.qameta.allure:allure-testng:2.25.0")
    
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    
    // dotenv for environment variables
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    
    // JSON handling
    implementation("com.google.code.gson:gson:2.10.1")
    
    // Removing AssertJ temporarily to fix compilation issues
    // testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useTestNG {
        suites("src/test/resources/testng.xml")
    }
    
    systemProperty("allure.results.directory", "build/allure-results")
    
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

configurations {
    all {
        exclude(group = "io.qameta.allure", module = "allure-testng")
    }
}

tasks.named<Test>("test") {
    include("basictest/**")
    exclude("tests/**")
}

sourceSets {
    test {
        kotlin {
            exclude("**/tests/**")
        }
    }
}

allure {
    version.set("2.25.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
