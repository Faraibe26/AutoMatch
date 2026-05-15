#!/bin/bash

# AutoMatch AI - Deployment Ready Checklist
# This script verifies all components are in place and ready to run

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m'

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

print_header() {
    echo -e "\n${BLUE}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}\n"
}

check_file() {
    if [ -f "$1" ]; then
        echo -e "${GREEN}✓${NC} $1"
        return 0
    else
        echo -e "${RED}✗${NC} $1 (MISSING)"
        return 1
    fi
}

check_dir() {
    if [ -d "$1" ]; then
        echo -e "${GREEN}✓${NC} $1"
        return 0
    else
        echo -e "${RED}✗${NC} $1 (MISSING)"
        return 1
    fi
}

PASSED=0
FAILED=0

# Backend Files
print_header "Backend Spring Boot Files"
check_file "$PROJECT_DIR/pom.xml" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/resources/application.properties" && ((PASSED++)) || ((FAILED++))
check_dir "$PROJECT_DIR/src/main/java/com/automatch" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/AutoMatchAiApplication.java" && ((PASSED++)) || ((FAILED++))

# Backend Controllers
print_header "Backend Controllers"
check_file "$PROJECT_DIR/src/main/java/com/automatch/controllers/AuthController.java" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/controllers/CarController.java" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/controllers/RecommendationController.java" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/controllers/FavoriteCarController.java" && ((PASSED++)) || ((FAILED++))

# Backend Services
print_header "Backend Services"
check_file "$PROJECT_DIR/src/main/java/com/automatch/services/AuthService.java" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/services/CarService.java" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/services/RecommendationService.java" && ((PASSED++)) || ((FAILED++))

# Backend Models
print_header "Backend Models"
check_file "$PROJECT_DIR/src/main/java/com/automatch/models/User.java" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/models/Car.java" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/models/Recommendation.java" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/src/main/java/com/automatch/models/FavoriteCar.java" && ((PASSED++)) || ((FAILED++))

# Frontend Files
print_header "Frontend React Files"
check_file "$PROJECT_DIR/frontend/package.json" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/tailwind.config.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/.env" && ((PASSED++)) || ((FAILED++))
check_dir "$PROJECT_DIR/frontend/src" && ((PASSED++)) || ((FAILED++))

# Frontend Pages
print_header "Frontend Pages"
check_file "$PROJECT_DIR/frontend/src/pages/Login.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/src/pages/Register.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/src/pages/Dashboard.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/src/pages/Recommendations.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/src/pages/Favorites.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/src/App.js" && ((PASSED++)) || ((FAILED++))

# Frontend Components
print_header "Frontend Components"
check_file "$PROJECT_DIR/frontend/src/components/Navbar.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/src/components/CarCard.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/src/components/ProtectedRoute.js" && ((PASSED++)) || ((FAILED++))

# Frontend Context & Services
print_header "Frontend Context & Services"
check_file "$PROJECT_DIR/frontend/src/context/AuthContext.js" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/frontend/src/services/api.js" && ((PASSED++)) || ((FAILED++))

# Configuration Files
print_header "Configuration & Documentation"
check_file "$PROJECT_DIR/START_PROJECT.md" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/start-all.sh" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/AUTOMATCH_README.md" && ((PASSED++)) || ((FAILED++))
check_file "$PROJECT_DIR/API_DOCUMENTATION.md" && ((PASSED++)) || ((FAILED++))

# Summary
print_header "Summary"
TOTAL=$((PASSED + FAILED))
echo -e "${GREEN}Passed: $PASSED${NC}"
echo -e "${RED}Failed: $FAILED${NC}"
echo -e "Total: $TOTAL\n"

if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}✓ All files are in place and ready!${NC}"
    echo -e "\n${YELLOW}Next steps:${NC}"
    echo -e "1. Start backend:  ${BLUE}cd $PROJECT_DIR && ./start-all.sh${NC}"
    echo -e "2. Or separately:"
    echo -e "   - Backend:  ${BLUE}cd $PROJECT_DIR && mvn clean spring-boot:run${NC}"
    echo -e "   - Frontend: ${BLUE}cd $PROJECT_DIR/frontend && npm start${NC}"
    echo -e "\n${YELLOW}Access the application:${NC}"
    echo -e "  - Frontend: ${BLUE}http://localhost:3000${NC}"
    echo -e "  - Backend:  ${BLUE}http://localhost:8080/api${NC}"
    echo -e "  - Swagger:  ${BLUE}http://localhost:8080/api/swagger-ui.html${NC}"
    exit 0
else
    echo -e "${RED}✗ Some files are missing! Please check the errors above.${NC}"
    exit 1
fi
