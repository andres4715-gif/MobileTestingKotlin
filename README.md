# 📱 Mobile Testing Framework - Kotlin + Appium + BrowserStack

[![Mobile Tests](https://github.com/yourusername/MobileTestingKotlin/actions/workflows/mobile-tests.yml/badge.svg)](https://github.com/yourusername/MobileTestingKotlin/actions/workflows/mobile-tests.yml)

Framework moderno de automatización de pruebas móviles para Android e iOS usando Kotlin, Appium y BrowserStack Cloud.

## 🌟 Características

- ✅ **Multiplataforma**: Soporte para Android e iOS con el mismo código
- ☁️ **BrowserStack Integration**: Ejecución en la nube desde día 1
- 🎯 **Page Object Model**: Arquitectura limpia y mantenible
- 📊 **Reportes Allure**: Reportes visuales detallados con screenshots
- 🔄 **CI/CD con GitHub Actions**: Integración continua automática
- 🔐 **Variables de Entorno**: Gestión segura de credenciales
- 🧪 **7 Tests de Ejemplo**: Tests listos para ejecutar
- 📸 **Screenshots Automáticos**: Capturas en fallos y éxitos

## 🏗️ Arquitectura del Framework

```
MobileTestingKotlin/
├── .github/workflows/          # GitHub Actions CI/CD
│   ├── mobile-tests.yml       # Pipeline principal
│   └── scheduled-tests.yml    # Tests programados
├── src/
│   ├── main/kotlin/
│   │   ├── config/           # Configuración y capabilities
│   │   │   ├── ConfigManager.kt
│   │   │   └── BrowserStackCapabilities.kt
│   │   ├── drivers/          # Gestión de drivers
│   │   │   ├── DriverManager.kt
│   │   │   └── DriverFactory.kt
│   │   ├── pages/            # Page Object Model
│   │   │   ├── BasePage.kt
│   │   │   └── SamplePage.kt
│   │   └── utils/            # Utilidades
│   │       ├── WaitUtils.kt
│   │       ├── ScreenshotUtils.kt
│   │       └── ElementUtils.kt
│   └── test/
│       ├── kotlin/tests/     # Tests
│       │   ├── BaseTest.kt
│       │   └── SampleTests.kt
│       └── resources/
│           ├── testng.xml
│           ├── allure.properties
│           └── logback-test.xml
├── .env.example              # Ejemplo de variables de entorno
├── build.gradle.kts          # Configuración Gradle
└── README.md
```

## 📋 Requisitos Previos

- **Java JDK 17** o superior
- **Gradle 8+** (incluido en el wrapper)
- **Cuenta de BrowserStack** ([Crear cuenta gratuita](https://www.browserstack.com/users/sign_up))
- **App en BrowserStack** (Android APK o iOS IPA)

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone https://github.com/yourusername/MobileTestingKotlin.git
cd MobileTestingKotlin
```

### 2. Subir tu App a BrowserStack

**Opción A: Mediante la Web UI**
1. Ir a [BrowserStack App Live](https://app-live.browserstack.com/)
2. Subir tu APK (Android) o IPA (iOS)
3. Copiar el `app_url` (formato: `bs://xxxxxx`)

**Opción B: Mediante cURL**

```bash
# Para Android
curl -u "USERNAME:ACCESS_KEY" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@/path/to/your/app.apk"

# Para iOS
curl -u "USERNAME:ACCESS_KEY" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@/path/to/your/app.ipa"
```

### 3. Configurar Variables de Entorno

```bash
# Copiar el archivo de ejemplo
cp .env.example .env

# Editar .env con tus credenciales
nano .env
```

**Contenido del archivo `.env`:**

```properties
# BrowserStack Credentials
BROWSERSTACK_USERNAME=tu_username_browserstack
BROWSERSTACK_ACCESS_KEY=tu_access_key_browserstack

# App URLs (obtenidos al subir la app)
ANDROID_APP_URL=bs://xxxxxxxxxxxxx
IOS_APP_URL=bs://xxxxxxxxxxxxx

# Test Configuration
PLATFORM=android                    # android o ios
DEVICE_NAME=Google Pixel 7         # Nombre del dispositivo
OS_VERSION=13.0                    # Versión del OS

# BrowserStack Configuration
BROWSERSTACK_PROJECT_NAME=Mobile Testing Framework
BROWSERSTACK_BUILD_NAME=Build_1.0
BROWSERSTACK_DEBUG=true
BROWSERSTACK_NETWORK_LOGS=true
```

### 4. Dar Permisos al Gradle Wrapper

```bash
chmod +x gradlew
```

## ▶️ Ejecutar Tests

### Ejecutar Todos los Tests

```bash
./gradlew clean test
```

### Ejecutar Tests para Android

```bash
export PLATFORM=android
export DEVICE_NAME="Google Pixel 7"
export OS_VERSION="13.0"
./gradlew clean test
```

### Ejecutar Tests para iOS

```bash
export PLATFORM=ios
export DEVICE_NAME="iPhone 14 Pro"
export OS_VERSION="16"
./gradlew clean test
```

### Ejecutar Test Específico

```bash
./gradlew test --tests "tests.SampleTests.testAppLaunch"
```

### Ejecutar con Logs Detallados

```bash
./gradlew clean test --info
```

## 📊 Generar y Ver Reportes

### Generar Reporte Allure

```bash
# Generar reporte
./gradlew allureReport

# Abrir reporte en el navegador (requiere Allure CLI)
./gradlew allureServe
```

### Instalar Allure CLI (Opcional)

```bash
# MacOS
brew install allure

# Linux
sudo apt-get install allure

# Después puedes usar:
allure serve build/allure-results
```

### Ver Reportes de TestNG

Los reportes de TestNG se generan automáticamente en:
```
build/reports/tests/test/index.html
```

### Ver Screenshots

Los screenshots se guardan en:
```
screenshots/
```

## 🔧 Configuración de GitHub Actions

### 1. Configurar Secrets en GitHub

Ve a tu repositorio en GitHub → Settings → Secrets and variables → Actions

Agrega los siguientes secrets:

| Secret Name | Descripción |
|-------------|-------------|
| `BROWSERSTACK_USERNAME` | Tu usuario de BrowserStack |
| `BROWSERSTACK_ACCESS_KEY` | Tu access key de BrowserStack |
| `ANDROID_APP_URL` | URL de tu app Android (bs://xxx) |
| `IOS_APP_URL` | URL de tu app iOS (bs://xxx) |

### 2. Ejecutar Tests desde GitHub Actions

**Automático:**
- Los tests se ejecutan automáticamente en cada push a `main` o `develop`
- También se ejecutan en Pull Requests

**Manual:**
1. Ve a Actions → Mobile Tests on BrowserStack
2. Click en "Run workflow"
3. Selecciona la plataforma (android/ios)
4. Click en "Run workflow"

### 3. Ver Resultados

1. Ve a la pestaña "Actions" en GitHub
2. Selecciona el workflow ejecutado
3. Descarga los artifacts:
   - `allure-report-android/ios`: Reporte completo
   - `screenshots-android/ios`: Capturas de pantalla
   - `test-report-android/ios`: Reporte TestNG

## 📝 Escribir Nuevos Tests

### 1. Crear una Nueva Page Object

```kotlin
package pages

import org.openqa.selenium.By

class LoginPage : BasePage() {
    
    private val usernameField: By = By.id("username")
    private val passwordField: By = By.id("password")
    private val loginButton: By = By.id("login_btn")
    
    override fun waitForPageToLoad() {
        waitForVisible(loginButton)
    }
    
    override fun isPageLoaded(): Boolean {
        return isDisplayed(loginButton)
    }
    
    fun login(username: String, password: String) {
        type(usernameField, username)
        type(passwordField, password)
        click(loginButton)
    }
}
```

### 2. Crear un Nuevo Test

```kotlin
package tests

import io.qameta.allure.*
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test
import pages.LoginPage

@Epic("Authentication")
@Feature("Login")
class LoginTests : BaseTest() {
    
    @Test(description = "Verify successful login")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Login")
    fun testSuccessfulLogin() {
        step("Navigate to login page") {
            val loginPage = LoginPage()
            loginPage.waitForPageToLoad()
        }
        
        step("Enter credentials and login") {
            val loginPage = LoginPage()
            loginPage.login("testuser", "password123")
        }
        
        step("Verify user is logged in") {
            // Add your verification logic
            assertThat(true).isTrue()
        }
    }
}
```

### 3. Agregar el Test a testng.xml

```xml
<class name="tests.LoginTests">
    <methods>
        <include name="testSuccessfulLogin"/>
    </methods>
</class>
```

## 🎯 Mejores Prácticas Implementadas

### 1. **Page Object Model (POM)**
- Separación clara entre lógica de test y elementos de la UI
- Reutilización de código
- Fácil mantenimiento

### 2. **Principios SOLID**
- Single Responsibility: Cada clase tiene una responsabilidad única
- Open/Closed: Extensible sin modificar código existente
- Dependency Inversion: Uso de abstracciones

### 3. **Wait Strategies**
- Esperas explícitas sobre implícitas
- Timeouts configurables
- Manejo de elementos dinámicos

### 4. **Gestión de Screenshots**
- Screenshots automáticos en fallos
- Adjuntos a reportes Allure
- Organizados por test

### 5. **Logging**
- Logs estructurados con Logback
- Diferentes niveles de log
- Rotación automática de archivos

## 🛠️ Troubleshooting

### Error: "Driver not initialized"

**Solución:** Verifica que las variables de entorno estén configuradas correctamente.

```bash
cat .env
```

### Error: "Unable to create session"

**Solución:** 
1. Verifica tus credenciales de BrowserStack
2. Asegúrate de que el `app_url` sea correcto
3. Verifica que el dispositivo esté disponible en BrowserStack

```bash
# Verificar dispositivos disponibles
curl -u "USERNAME:ACCESS_KEY" \
  https://api-cloud.browserstack.com/app-automate/devices.json
```

### Tests muy lentos

**Solución:**
1. Reduce los timeouts en `WaitUtils.kt`
2. Usa esperas más específicas
3. Verifica tu conexión a Internet

### Error: "App not found"

**Solución:** Re-sube tu app a BrowserStack:

```bash
curl -u "USERNAME:ACCESS_KEY" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@/path/to/app.apk"
```

## 📚 Recursos Adicionales

- [Documentación de Appium](https://appium.io/docs/en/latest/)
- [BrowserStack App Automate](https://www.browserstack.com/app-automate)
- [Documentación de Allure](https://docs.qameta.io/allure/)
- [TestNG Documentation](https://testng.org/doc/documentation-main.html)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## ✨ Características Futuras

- [ ] Integración con más proveedores cloud (Sauce Labs, AWS Device Farm)
- [ ] Soporte para pruebas visuales
- [ ] Integración con JIRA
- [ ] Tests de rendimiento
- [ ] Pruebas de accesibilidad
- [ ] Soporte para múltiples lenguajes

## 📧 Contacto

Para preguntas y soporte, abre un issue en GitHub.

---

⭐ **¡Si te gusta este proyecto, dale una estrella en GitHub!** ⭐
