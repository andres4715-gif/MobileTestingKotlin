# 🚀 Guía de Inicio Rápido

Esta guía te llevará desde cero hasta ejecutar tu primer test en menos de 10 minutos.

## ✅ Checklist Pre-requisitos

- [ ] Java 17 instalado
- [ ] Cuenta de BrowserStack creada
- [ ] App subida a BrowserStack

## 📝 Pasos

### 1. Obtener Credenciales de BrowserStack

1. Ve a [BrowserStack Automate](https://automate.browserstack.com/)
2. Haz clic en "Access Key" en el dashboard
3. Copia tu `Username` y `Access Key`

### 2. Subir tu App

**Para Android:**
```bash
curl -u "TU_USERNAME:TU_ACCESS_KEY" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@/ruta/a/tu/app.apk"
```

**Respuesta:**
```json
{
  "app_url": "bs://c8ddcb5649a8280ca800075bfd8f151115bba6b3"
}
```

Guarda el `app_url`.

### 3. Configurar Variables de Entorno

```bash
# 1. Copiar archivo de ejemplo
cp .env.example .env

# 2. Editar con tus datos
nano .env
```

**Contenido mínimo del .env:**
```properties
BROWSERSTACK_USERNAME=tu_username
BROWSERSTACK_ACCESS_KEY=tu_access_key
ANDROID_APP_URL=bs://c8ddcb5649a8280ca800075bfd8f151115bba6b3
PLATFORM=android
```

### 4. Ejecutar Tests

```bash
# Dar permisos
chmod +x gradlew

# Ejecutar tests
./gradlew clean test
```

### 5. Ver Resultados

**En la Terminal:**
Los resultados se mostrarán en tiempo real.

**Reportes:**
```bash
# Ver reporte HTML
open build/reports/tests/test/index.html

# Ver reporte Allure (requiere Allure CLI)
./gradlew allureServe
```

**Screenshots:**
```bash
# Ver screenshots
open screenshots/
```

## 🎯 Ejecutar Tests Específicos

```bash
# Solo test de launch
./gradlew test --tests "tests.SampleTests.testAppLaunch"

# Solo tests críticos
./gradlew test --tests "*Critical*"
```

## 📱 Cambiar entre Android e iOS

**Para Android:**
```bash
export PLATFORM=android
export DEVICE_NAME="Google Pixel 7"
export OS_VERSION="13.0"
./gradlew test
```

**Para iOS:**
```bash
export PLATFORM=ios
export DEVICE_NAME="iPhone 14 Pro"
export OS_VERSION="16"
./gradlew test
```

## 🔍 Monitorear en BrowserStack

Mientras los tests corren:
1. Ve a [BrowserStack Dashboard](https://app-automate.browserstack.com/)
2. Verás los tests en ejecución en tiempo real
3. Puedes ver logs, screenshots y videos

## ⚡ Tips Rápidos

### Ver Dispositivos Disponibles
```bash
curl -u "USERNAME:KEY" \
  https://api-cloud.browserstack.com/app-automate/devices.json | json_pp
```

### Ver tus Apps Subidas
```bash
curl -u "USERNAME:KEY" \
  https://api-cloud.browserstack.com/app-automate/recent_apps
```

### Ejecutar con Logs Detallados
```bash
./gradlew test --info --stacktrace
```

## 🎉 ¡Felicidades!

Ahora tienes un framework de testing móvil funcionando. 

**Próximos pasos:**
1. Personalizar los tests para tu app
2. Agregar más Page Objects
3. Integrar con GitHub Actions
4. Ver el [README.md](README.md) completo para más detalles

## 🆘 Problemas Comunes

### "JAVA_HOME not set"
```bash
# Mac
export JAVA_HOME=$(/usr/libexec/java_home -v 17)

# Linux
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
```

### "Permission denied: ./gradlew"
```bash
chmod +x gradlew
```

### "Unable to create session"
- Verifica tus credenciales en `.env`
- Verifica que el `app_url` sea correcto
- Verifica que el dispositivo esté disponible

### Tests muy lentos
- Normal en la primera ejecución (descarga dependencias)
- Mejorará en ejecuciones posteriores

## 📚 Más Información

- [README.md](README.md) - Documentación completa
- [CONTRIBUTING.md](CONTRIBUTING.md) - Guía de contribución
