# ⚙️ Configuración de GitHub Actions

Esta guía explica cómo configurar GitHub Actions para ejecutar tests automáticamente en BrowserStack.

## 📋 Requisitos Previos

- Repositorio en GitHub
- Cuenta de BrowserStack
- App subida a BrowserStack

## 🔐 Paso 1: Configurar Secrets en GitHub

Los **secrets** son variables encriptadas que GitHub Actions usa para almacenar información sensible.

### Agregar Secrets

1. **Ve a tu repositorio en GitHub**
2. **Click en `Settings`** (⚙️)
3. **En el menú izquierdo, click en `Secrets and variables`** → `Actions`
4. **Click en `New repository secret`**

### Secrets Requeridos

Agrega estos **4 secrets** uno por uno:

| Secret Name | Valor | Dónde Obtenerlo |
|-------------|-------|-----------------|
| `BROWSERSTACK_USERNAME` | Tu usuario de BrowserStack | [Dashboard BrowserStack](https://automate.browserstack.com/) |
| `BROWSERSTACK_ACCESS_KEY` | Tu access key de BrowserStack | [Dashboard BrowserStack](https://automate.browserstack.com/) |
| `ANDROID_APP_URL` | URL de tu app Android (bs://xxx) | Resultado del upload de APK |
| `IOS_APP_URL` | URL de tu app iOS (bs://xxx) | Resultado del upload de IPA |

### Ejemplo de Configuración

```
Nombre: BROWSERSTACK_USERNAME
Valor: john_doe_ABC123

Nombre: BROWSERSTACK_ACCESS_KEY
Valor: aBcDeFgHiJkLmN1234567890

Nombre: ANDROID_APP_URL
Valor: bs://c8ddcb5649a8280ca800075bfd8f151115bba6b3

Nombre: IOS_APP_URL
Valor: bs://d9eecb6759b9391db900086cge9f262226ccb7c4
```

## 🚀 Paso 2: Ejecutar Workflows

### Ejecución Automática

Los tests se ejecutan automáticamente en:
- Push a ramas `main` o `develop`
- Pull Requests a `main` o `develop`

### Ejecución Manual

1. **Ve a la pestaña `Actions`** en tu repositorio
2. **Selecciona el workflow** "Mobile Tests on BrowserStack"
3. **Click en `Run workflow`**
4. **Selecciona la plataforma** (android o ios)
5. **Click en `Run workflow`**

## 📊 Paso 3: Ver Resultados

### Durante la Ejecución

1. Ve a `Actions` → Click en el workflow en ejecución
2. Verás el progreso en tiempo real
3. Los logs se actualizan automáticamente

### Después de la Ejecución

**Artifacts Generados:**
- `allure-report-android/ios`: Reporte Allure completo
- `screenshots-android/ios`: Capturas de pantalla
- `test-report-android/ios`: Reporte TestNG

**Para descargar:**
1. Ve al workflow completado
2. Scroll hacia abajo hasta "Artifacts"
3. Click en el artifact para descargar

## 📈 Paso 4: Publicar Reportes en GitHub Pages (Opcional)

### Habilitar GitHub Pages

1. Ve a `Settings` → `Pages`
2. En "Source", selecciona `Deploy from a branch`
3. En "Branch", selecciona `gh-pages` y `/root`
4. Click en `Save`

### Acceder a los Reportes

Después de la primera ejecución:
```
https://tuusuario.github.io/MobileTestingKotlin/
```

## 🔄 Tests Programados

El workflow `scheduled-tests.yml` ejecuta tests automáticamente todos los días a las 2 AM UTC.

**Modificar horario:**

Edita `.github/workflows/scheduled-tests.yml`:

```yaml
schedule:
  # Cada día a las 8 AM
  - cron: '0 8 * * *'
  
  # Cada lunes a las 9 AM
  - cron: '0 9 * * 1'
  
  # Cada hora
  - cron: '0 * * * *'
```

[Generador de Cron](https://crontab.guru/)

## ⚠️ Notas Importantes

### Sobre los Warnings del Linter

Es normal ver warnings como:
```
Context access might be invalid: BROWSERSTACK_USERNAME
```

**Estos warnings son esperados y no son errores.** GitHub Actions los muestra porque los secrets se deben configurar en el repositorio (no están en el código). Una vez configures los secrets, los workflows funcionarán correctamente.

### Límites de GitHub Actions

**Free tier:**
- 2,000 minutos/mes para repositorios privados
- Ilimitado para repositorios públicos

**Pro tier:**
- 3,000 minutos/mes

Los tests móviles típicamente usan ~5-10 minutos por ejecución.

### Seguridad de Secrets

- Los secrets están encriptados
- No se muestran en los logs
- Solo están disponibles durante la ejecución
- No son accesibles en Pull Requests de forks (por seguridad)

## 🎯 Workflows Disponibles

### 1. Mobile Tests (`mobile-tests.yml`)

**Ejecuta:** Tests en Android o iOS
**Trigger:** Push, PR, Manual
**Duración:** ~5-15 minutos

### 2. Scheduled Tests (`scheduled-tests.yml`)

**Ejecuta:** Tests programados de Android
**Trigger:** Diariamente, Manual
**Duración:** ~5-15 minutos

## 🔧 Personalización

### Agregar Más Plataformas

Edita `mobile-tests.yml`:

```yaml
test-multiple-devices:
  strategy:
    matrix:
      include:
        - platform: android
          device: Google Pixel 7
          os: 13.0
        - platform: android
          device: Samsung Galaxy S23
          os: 13.0
        - platform: ios
          device: iPhone 14 Pro
          os: 16
```

### Agregar Notificaciones

**Slack:**
```yaml
- name: Notify Slack
  if: always()
  uses: 8398a7/action-slack@v3
  with:
    status: ${{ job.status }}
    webhook_url: ${{ secrets.SLACK_WEBHOOK }}
```

**Email:**
```yaml
- name: Send Email
  if: failure()
  uses: dawidd6/action-send-mail@v3
  with:
    server_address: smtp.gmail.com
    server_port: 465
    username: ${{ secrets.EMAIL_USERNAME }}
    password: ${{ secrets.EMAIL_PASSWORD }}
    subject: Tests Failed
    to: team@example.com
```

## 🆘 Troubleshooting

### Error: "Secret not found"

**Solución:** Verifica que agregaste el secret con el nombre exacto (case-sensitive).

### Error: "Unable to create session"

**Solución:** Verifica que los valores de los secrets sean correctos.

### Workflow no se ejecuta

**Soluciones:**
1. Verifica que el archivo esté en `.github/workflows/`
2. Verifica la sintaxis YAML
3. Verifica que los triggers estén configurados correctamente

### Tests pasan localmente pero fallan en Actions

**Posibles causas:**
1. Variables de entorno diferentes
2. Timeouts muy cortos
3. Dispositivos no disponibles en BrowserStack

## 📚 Recursos

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [BrowserStack REST API](https://www.browserstack.com/docs/app-automate/api-reference/introduction)
- [Allure Report Action](https://github.com/simple-elf/allure-report-action)

## ✅ Checklist de Configuración

- [ ] Secrets agregados en GitHub
- [ ] Primer workflow ejecutado exitosamente
- [ ] Artifacts descargados y verificados
- [ ] GitHub Pages configurado (opcional)
- [ ] Tests programados configurados (opcional)
- [ ] Notificaciones configuradas (opcional)

---

¿Problemas con la configuración? Abre un [issue](../../issues) en GitHub.
