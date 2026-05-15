#!/bin/bash

# AutoMatch AI - Complete Startup Script
# Automatically starts both backend and frontend

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_DIR="$PROJECT_DIR"
FRONTEND_DIR="$PROJECT_DIR/frontend"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

# Banner
clear
echo -e "${CYAN}"
cat << "EOF"
╔═══════════════════════════════════════════════════════════╗
║                                                           ║
║          🚗 AutoMatch AI - Starting Services 🚗           ║
║         AI-Powered Car Recommendation Engine             ║
║                                                           ║
╚═══════════════════════════════════════════════════════════╝
EOF
echo -e "${NC}\n"

# Function to check if port is in use
check_port() {
    if lsof -Pi :$1 -sTCP:LISTEN -t >/dev/null 2>&1; then
        return 0  # Port is in use
    else
        return 1  # Port is free
    fi
}

# Function to start backend
start_backend() {
    echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${BLUE}Starting Backend (Spring Boot)${NC}"
    echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}\n"
    
    # Check port 8080
    if check_port 8080; then
        echo -e "${RED}✗ Port 8080 is already in use${NC}"
        echo "Please stop the process using port 8080 first"
        exit 1
    fi
    
    cd "$BACKEND_DIR"
    
    # Try Maven first
    if command -v mvn &> /dev/null; then
        echo -e "${GREEN}✓ Maven found${NC}"
        echo "Running: mvn clean spring-boot:run"
        mvn clean spring-boot:run &
        BACKEND_PID=$!
    else
        echo -e "${YELLOW}⚠ Maven not found, attempting to install via Homebrew...${NC}"
        
        # Check if Homebrew is available
        if command -v brew &> /dev/null; then
            echo "Installing Maven..."
            brew install maven
            echo -e "${GREEN}✓ Maven installed${NC}"
            echo "Running: mvn clean spring-boot:run"
            mvn clean spring-boot:run &
            BACKEND_PID=$!
        else
            echo -e "${RED}✗ Homebrew not found. Please install Maven manually:${NC}"
            echo "  brew install maven"
            echo "  # or"
            echo "  # Download from https://maven.apache.org/download.cgi"
            exit 1
        fi
    fi
    
    echo -e "${GREEN}✓ Backend starting (PID: $BACKEND_PID)${NC}\n"
    
    # Wait for backend to be ready
    echo "Waiting for backend to start (http://localhost:8080/api)..."
    for i in {1..60}; do
        if curl -s http://localhost:8080/api/cars > /dev/null 2>&1; then
            echo -e "${GREEN}✓ Backend is ready!${NC}\n"
            return 0
        fi
        printf "."
        sleep 1
    done
    
    echo -e "${RED}✗ Backend failed to start${NC}"
    return 1
}

# Function to start frontend
start_frontend() {
    echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${BLUE}Starting Frontend (React)${NC}"
    echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}\n"
    
    # Check port 3000
    if check_port 3000; then
        echo -e "${RED}✗ Port 3000 is already in use${NC}"
        echo "Please stop the process using port 3000 first"
        exit 1
    fi
    
    cd "$FRONTEND_DIR"
    
    # Check if node_modules exist
    if [ ! -d "node_modules" ]; then
        echo "Installing npm dependencies..."
        npm install
        echo ""
    fi
    
    echo "Running: npm start"
    npm start &
    FRONTEND_PID=$!
    
    echo -e "${GREEN}✓ Frontend starting (PID: $FRONTEND_PID)${NC}\n"
    
    # Wait for frontend to be ready
    echo "Waiting for frontend to start (http://localhost:3000)..."
    for i in {1..60}; do
        if curl -s http://localhost:3000 > /dev/null 2>&1; then
            echo -e "${GREEN}✓ Frontend is ready!${NC}\n"
            return 0
        fi
        printf "."
        sleep 1
    done
    
    echo -e "${YELLOW}⚠ Frontend may still be compiling... continuing${NC}\n"
    return 0
}

# Function to print summary
print_summary() {
    echo -e "\n${GREEN}"
    cat << "EOF"
╔═══════════════════════════════════════════════════════════╗
║                                                           ║
║         ✓ All Services Running Successfully! ✓           ║
║                                                           ║
╠═══════════════════════════════════════════════════════════╣
║                                                           ║
║  🚀 BACKEND                                             ║
║     API:      http://localhost:8080/api                ║
║     Swagger:  http://localhost:8080/api/swagger-ui     ║
║     H2 DB:    http://localhost:8080/api/h2-console    ║
║                                                           ║
║  💻 FRONTEND                                            ║
║     App:      http://localhost:3000                    ║
║                                                           ║
║  📝 DEFAULT TEST CREDENTIALS                            ║
║     Email:    demo@automatch.com                       ║
║     Password: Demo@123456                              ║
║                                                           ║
║  📚 DOCUMENTATION                                        ║
║     Setup:    START_PROJECT.md                         ║
║     API:      API_DOCUMENTATION.md                     ║
║     README:   AUTOMATCH_README.md                      ║
║                                                           ║
╠═══════════════════════════════════════════════════════════╣
║  Press Ctrl+C to stop all services                      ║
╚═══════════════════════════════════════════════════════════╝
EOF
    echo -e "${NC}"
}

# Cleanup function for Ctrl+C
cleanup() {
    echo -e "\n${YELLOW}Shutting down services...${NC}"
    kill $BACKEND_PID 2>/dev/null || true
    kill $FRONTEND_PID 2>/dev/null || true
    sleep 1
    echo -e "${GREEN}✓ All services stopped${NC}"
    exit 0
}

# Set trap for Ctrl+C
trap cleanup SIGINT SIGTERM

# Main execution
start_backend
start_frontend
print_summary

# Keep script running
wait
