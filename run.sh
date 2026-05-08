#!/bin/bash

# Banking Management System - Compile and Run Script
# Simple script for compiling and running the application

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC_DIR="$PROJECT_DIR/src/main/java"
BUILD_DIR="$PROJECT_DIR/build"
CLASSES_DIR="$BUILD_DIR/classes"

echo "=========================================="
echo "  Banking Management System"
echo "  Build and Run Script"
echo "=========================================="
echo ""

# Create build directory
mkdir -p "$CLASSES_DIR"

# Compile source files
echo "Compiling source code..."
find "$SRC_DIR" -name "*.java" -type f | head -5 | while read -r file; do
    echo "  Compiling: $(basename "$file")"
done

javac -d "$CLASSES_DIR" -encoding UTF-8 \
    $(find "$SRC_DIR" -name "*.java" -type f)

if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Compilation successful!"
    echo ""
    echo "Starting Banking Management System..."
    echo "=========================================="
    echo ""
    
    # Run the application
    cd "$PROJECT_DIR"
    java -cp "$CLASSES_DIR" com.banking.BankingApp
else
    echo ""
    echo "✗ Compilation failed! Please check the errors above."
    exit 1
fi
