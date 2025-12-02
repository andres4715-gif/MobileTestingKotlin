#!/bin/bash

###############################################################################
# Script de Validación del Framework
# Verifica que todos los componentes estén correctamente configurados
###############################################################################

set -e

echo "╔════════════════════════════════════════════════════════════╗"
echo "║     Framework Validation Script                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Colors
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counters
PASSED=0
FAILED=0

# Function to print success
print_success() {
    echo -e "${GREEN}✓${NC} $1"
    ((PASSED++))
}

# Function to print error
print_error() {
    echo -e "${RED}✗${NC} $1"
    ((FAILED++))
}

# Function to print warning
print_warning() {
    echo -e "${YELLOW}⚠${NC} $1"
}

echo "🔍 Validando estructura del proyecto..."
echo ""

# 1. Check Java
echo "1. Verificando Java..."
if command -v java &> /dev/null; then
    JAVA_INFO=$(java -version 2>&1 | head -n 1)
    print_success "Java encontrado: $JAVA_INFO"
else
    print_error "Java no encontrado"
fi

# 2. Check Gradle wrapper
echo "2. Verificando Gradle wrapper..."
if [ -f "gradlew" ]; then
    if [ -x "gradlew" ]; then
        print_success "gradlew existe y es ejecutable"
    else
        print_warning "gradlew existe pero no es ejecutable. Ejecuta: chmod +x gradlew"
    fi
else
    print_error "gradlew no encontrado"
fi

# 3. Check .env file
echo "3. Verificando configuración..."
if [ -f ".env" ]; then
    print_success "Archivo .env encontrado"
    
    # Check required variables
    if grep -q "BROWSERSTACK_USERNAME=" .env && [ "$(grep BROWSERSTACK_USERNAME= .env | cut -d'=' -f2)" != "" ]; then
        print_success "BROWSERSTACK_USERNAME configurado"
    else
        print_warning "BROWSERSTACK_USERNAME no configurado en .env"
    fi
    
    if grep -q "BROWSERSTACK_ACCESS_KEY=" .env && [ "$(grep BROWSERSTACK_ACCESS_KEY= .env | cut -d'=' -f2)" != "" ]; then
        print_success "BROWSERSTACK_ACCESS_KEY configurado"
    else
        print_warning "BROWSERSTACK_ACCESS_KEY no configurado en .env"
    fi
else
    print_warning "Archivo .env no encontrado. Copia .env.example a .env"
fi

# 4. Check source structure
echo "4. Verificando estructura de código..."
REQUIRED_DIRS=(
    "src/main/kotlin/config"
    "src/main/kotlin/drivers"
    "src/main/kotlin/pages"
    "src/main/kotlin/utils"
    "src/test/kotlin/tests"
    "src/test/resources"
)

for dir in "${REQUIRED_DIRS[@]}"; do
    if [ -d "$dir" ]; then
        print_success "Directorio $dir existe"
    else
        print_error "Directorio $dir no encontrado"
    fi
done

# 5. Check key files
echo "5. Verificando archivos clave..."
KEY_FILES=(
    "build.gradle.kts"
    "settings.gradle.kts"
    "src/main/kotlin/config/ConfigManager.kt"
    "src/main/kotlin/drivers/DriverManager.kt"
    "src/test/kotlin/tests/BaseTest.kt"
    "src/test/kotlin/tests/SampleTests.kt"
    "src/test/resources/testng.xml"
)

for file in "${KEY_FILES[@]}"; do
    if [ -f "$file" ]; then
        print_success "Archivo $file existe"
    else
        print_error "Archivo $file no encontrado"
    fi
done

# 6. Check documentation
echo "6. Verificando documentación..."
DOCS=(
    "README.md"
    "QUICKSTART.md"
    "GITHUB_ACTIONS_SETUP.md"
    "CONTRIBUTING.md"
)

for doc in "${DOCS[@]}"; do
    if [ -f "$doc" ]; then
        print_success "Documentación $doc existe"
    else
        print_error "Documentación $doc no encontrada"
    fi
done

# 7. Check GitHub Actions
echo "7. Verificando GitHub Actions..."
if [ -f ".github/workflows/mobile-tests.yml" ]; then
    print_success "Workflow mobile-tests.yml existe"
else
    print_error "Workflow mobile-tests.yml no encontrado"
fi

if [ -f ".github/workflows/scheduled-tests.yml" ]; then
    print_success "Workflow scheduled-tests.yml existe"
else
    print_error "Workflow scheduled-tests.yml no encontrado"
fi

# 8. Try to compile
echo "8. Intentando compilar el proyecto..."
if [ -x "gradlew" ]; then
    if ./gradlew compileKotlin compileTestKotlin --quiet 2>/dev/null; then
        print_success "Compilación exitosa"
    else
        print_warning "Compilación falló o tiene warnings (puede ser normal si faltan dependencias)"
    fi
else
    print_warning "No se puede compilar (gradlew no ejecutable)"
fi

# Summary
echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║                     RESUMEN                                ║"
echo "╠════════════════════════════════════════════════════════════╣"
echo -e "║ ${GREEN}Exitosos:${NC} $PASSED"
echo -e "║ ${RED}Fallidos:${NC} $FAILED"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}✓ Validación completada exitosamente!${NC}"
    echo ""
    echo "Próximos pasos:"
    echo "1. Configura .env con tus credenciales de BrowserStack"
    echo "2. Ejecuta: ./gradlew clean test"
    echo "3. Ve los reportes: ./gradlew allureServe"
    echo ""
    echo "Consulta QUICKSTART.md para más detalles."
    exit 0
else
    echo -e "${RED}✗ Se encontraron $FAILED problemas${NC}"
    echo ""
    echo "Revisa los errores arriba y consulta README.md para soluciones."
    exit 1
fi
