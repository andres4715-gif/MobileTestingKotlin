# Instrucciones de Configuración

## Introducción

Este framework de automatización móvil con Kotlin y Appium permite ejecutar pruebas en BrowserStack. Este documento proporciona instrucciones detalladas para configurar y utilizar el framework.

## Requisitos Previos

- **Java JDK 17 o superior**
- **Gradle** (incluido como wrapper)
- **Cuenta de BrowserStack** (requerido para pruebas en dispositivos reales)
- **APK/IPA de la aplicación** que desea probar

## Configuración Paso a Paso

### 1. Instalar Java

Para macOS:

```bash
# Instalar usando Homebrew
brew install openjdk@17

# Crear enlace simbólico
sudo ln -sfn $(brew --prefix)/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk

# Agregar a PATH en .zshrc o .bash_profile
echo 'export PATH="$(brew --prefix)/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

Para verificar la instalación:

```bash
java --version
```

### 2. Configurar Credenciales de BrowserStack

1. Crear una cuenta en [BrowserStack](https://www.browserstack.com/) si aún no tienes una
2. Copiar el archivo de ejemplo `.env.example` a `.env`:

```bash
cp .env.example .env
```

3. Editar el archivo `.env` con tus credenciales:

```properties
# BrowserStack Credentials
BROWSERSTACK_USERNAME=tu_username
BROWSERSTACK_ACCESS_KEY=tu_access_key

# App URLs en BrowserStack
ANDROID_APP_URL=bs://tu_app_id_android
IOS_APP_URL=bs://tu_app_id_ios

# Configuración de la Prueba
PLATFORM=android  # o ios
DEVICE_NAME=Google Pixel 7
OS_VERSION=13.0
```

### 3. Subir tu Aplicación a BrowserStack

#### Para Android:

```bash
curl -u "TU_USERNAME:TU_ACCESS_KEY" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@/ruta/a/tu/app.apk"
```

#### Para iOS:

```bash
curl -u "TU_USERNAME:TU_ACCESS_KEY" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@/ruta/a/tu/app.ipa"
```

Guarda el `app_url` devuelto (formato: `bs://xxxxx`) y actualiza el archivo `.env`.

### 4. Dar Permisos al Gradle Wrapper

```bash
chmod +x gradlew
```

## Ejecutando Tests

### Tests Básicos (sin BrowserStack)

Para ejecutar tests básicos que no requieren BrowserStack:

```bash
./gradlew clean test -Dmock=true
```

### Tests Completos en BrowserStack

```bash
./gradlew clean test
```

### Tests para una Plataforma Específica

```bash
# Para Android
export PLATFORM=android
export DEVICE_NAME="Google Pixel 7"
export OS_VERSION="13.0"
./gradlew clean test

# Para iOS
export PLATFORM=ios
export DEVICE_NAME="iPhone 14 Pro"
export OS_VERSION="16"
./gradlew clean test
```

## Estructura del Proyecto

```
MobileTestingKotlin/
├── .github/workflows/      # GitHub Actions CI/CD
├── src/
│   ├── main/kotlin/
│   │   ├── config/        # Configuración
│   │   ├── drivers/       # Drivers para iOS/Android
│   │   ├── pages/         # Page Objects
│   │   └── utils/         # Utilidades
│   └── test/
│       ├── kotlin/
│       │   ├── basictest/ # Tests básicos
│       │   └── tests/     # Tests avanzados
│       └── resources/
│           └── testng.xml # Configuración TestNG
├── .env.example           # Template de variables
├── build.gradle.kts       # Configuración Gradle
└── README.md              # Documentación
```

## Reportes y Screenshots

- **TestNG Reports**: `build/reports/tests/test/index.html`
- **Screenshots**: `screenshots/`

## Integración con GitHub Actions

Este framework incluye flujos de trabajo de GitHub Actions para ejecutar tests automáticamente. Para usarlos:

1. Sube tu código a GitHub
2. Configura los siguientes secrets en tu repositorio:
   - `BROWSERSTACK_USERNAME`
   - `BROWSERSTACK_ACCESS_KEY`
   - `ANDROID_APP_URL`
   - `IOS_APP_URL`
3. Los tests se ejecutarán automáticamente en cada push o pull request

## Solución de Problemas

### Error: "Unable to locate a Java Runtime"

Asegúrate de que Java está instalado y configurado correctamente:

```bash
java --version
echo $JAVA_HOME
```

### Error: "Could not find or load main class org.gradle.wrapper.GradleWrapperMain"

Verifica que el directorio `gradle/wrapper/` existe con los archivos necesarios.

### Error: "Unable to create session on BrowserStack"

- Verifica tus credenciales de BrowserStack
- Asegúrate de que la URL de la app es correcta
- Verifica que el dispositivo solicitado está disponible

### Error: "Execution failed for task ':compileKotlin'"

Esto puede deberse a incompatibilidades de dependencias o errores en el código. Intenta:

```bash
./gradlew clean build --stacktrace
```

## Contacto y Soporte

Para preguntas o problemas, crea un issue en el repositorio de GitHub o contacta al equipo de desarrollo.

---

Última actualización: Diciembre 2023
