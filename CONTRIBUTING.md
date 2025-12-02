# Guía de Contribución

¡Gracias por tu interés en contribuir al Mobile Testing Framework! 🎉

## Cómo Contribuir

### Reportar Bugs

Si encuentras un bug, por favor crea un issue con:

- Descripción clara del problema
- Pasos para reproducir
- Comportamiento esperado vs actual
- Screenshots si es posible
- Versión del framework
- Platform (Android/iOS)
- Logs relevantes

### Sugerir Mejoras

Las sugerencias son bienvenidas! Por favor incluye:

- Descripción clara de la mejora
- Justificación (por qué sería útil)
- Posible implementación
- Ejemplos de uso

### Pull Requests

1. Fork el repositorio
2. Crea una rama desde `develop`:
   ```bash
   git checkout -b feature/tu-feature
   ```

3. Haz tus cambios siguiendo las guías de estilo

4. Asegúrate de que los tests pasen:
   ```bash
   ./gradlew test
   ```

5. Commit con mensajes descriptivos:
   ```bash
   git commit -m "feat: agregar nueva funcionalidad X"
   ```

6. Push a tu fork:
   ```bash
   git push origin feature/tu-feature
   ```

7. Abre un Pull Request a `develop`

## Guías de Estilo

### Código Kotlin

- Usa Kotlin idiomático
- Sigue las convenciones de Kotlin
- Documenta funciones públicas
- Mantén funciones pequeñas y enfocadas
- Usa nombres descriptivos

### Tests

- Un test debe probar una sola cosa
- Usa nombres descriptivos
- Sigue el patrón AAA (Arrange, Act, Assert)
- Incluye anotaciones Allure

### Commits

Usa [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` nueva funcionalidad
- `fix:` corrección de bug
- `docs:` cambios en documentación
- `test:` agregar o modificar tests
- `refactor:` refactorización de código
- `chore:` cambios en build o dependencias

## Código de Conducta

- Sé respetuoso
- Acepta críticas constructivas
- Enfócate en lo mejor para el proyecto
- Muestra empatía

## Preguntas

Si tienes preguntas, abre un issue con la etiqueta `question`.

¡Gracias por contribuir! 🚀
