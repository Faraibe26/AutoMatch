#!/bin/bash

# AutoMatch AI - Quick Verification
# Checks all critical files are in place

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "\n${BLUE}AutoMatch AI - Deployment Verification${NC}\n"

MISSING=0

# Check Backend
echo -e "${BLUE}Backend Files:${NC}"
[ -f "$PROJECT_DIR/pom.xml" ] && echo -e "${GREEN}✓${NC} pom.xml" || { echo -e "${RED}✗${NC} pom.xml"; ((MISSING++)); }
[ -f "$PROJECT_DIR/src/main/resources/application.properties" ] && echo -e "${GREEN}✓${NC} application.properties" || { echo -e "${RED}✗${NC} application.properties"; ((MISSING++)); }
[ -f "$PROJECT_DIR/src/main/java/com/automatch/AutoMatchAiApplication.java" ] && echo -e "${GREEN}✓${NC} AutoMatchAiApplication.java" || { echo -e "${RED}✗${NC} AutoMatchAiApplication.java"; ((MISSING++)); }

# Check Frontend
echo -e "\n${BLUE}Frontend Files:${NC}"
[ -f "$PROJECT_DIR/frontend/package.json" ] && echo -e "${GREEN}✓${NC} package.json" || { echo -e "${RED}✗${NC} package.json"; ((MISSING++)); }
[ -f "$PROJECT_DIR/frontend/.env" ] && echo -e "${GREEN}✓${NC} .env" || { echo -e "${RED}✗${NC} .env"; ((MISSING++)); }
[ -f "$PROJECT_DIR/frontend/src/App.js" ] && echo -e "${GREEN}✓${NC} App.js" || { echo -e "${RED}✗${NC} App.js"; ((MISSING++)); }
[ -d "$PROJECT_DIR/frontend/src/pages" ] && echo -e "${GREEN}✓${NC} pages/" || { echo -e "${RED}✗${NC} pages/"; ((MISSING++)); }
[ -d "$PROJECT_DIR/frontend/src/components" ] && echo -e "${GREEN}✓${NC} components/" || { echo -e "${RED}✗${NC} components/"; ((MISSING++)); }

echo -e "\n${BLUE}Documentation:${NC}"
[ -f "$PROJECT_DIR/START_PROJECT.md" ] && echo -e "${GREEN}✓${NC} START_PROJECT.md" || { echo -e "${RED}✗${NC} START_PROJECT.md"; ((MISSING++)); }

if [ $MISSING -eq 0 ]; then
    echo -e "\n${GREEN}✓ All critical files present!${NC}"
    echo -e "\n${YELLOW}Ready to run:${NC}"
    echo -e "  Backend:  ${BLUE}cd $PROJECT_DIR && mvn clean spring-boot:run${NC}"
    echo -e "  Frontend: ${BLUE}cd $PROJECT_DIR/frontend && npm install && npm start${NC}"
    echo -e "\n${YELLOW}Or run both:${NC}"
    echo -e "  ${BLUE}./start-all.sh${NC}"
    exit 0
else
    echo -e "\n${RED}✗ Missing $MISSING critical files${NC}"
    exit 1
fi
