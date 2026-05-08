#!/bin/bash

# Banking Management System - Build and Run Script
# This script compiles and runs the application

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC_DIR="$PROJECT_DIR/src/main/java"
LIB_DIR="$PROJECT_DIR/lib"
BUILD_DIR="$PROJECT_DIR/build"
CLASSES_DIR="$BUILD_DIR/classes"

echo "=========================================="
echo "Banking Management System"
echo "Build and Run Script"
echo "=========================================="
echo ""

# Create build directories
mkdir -p "$CLASSES_DIR"
mkdir -p "$LIB_DIR"

# Download GSON library if not present
if [ ! -f "$LIB_DIR/gson-2.10.1.jar" ]; then
    echo "Downloading GSON library..."
    curl -o "$LIB_DIR/gson-2.10.1.jar" \
        https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar \
        || echo "Warning: Could not download GSON, trying to proceed without it"
fi

# Compile source files
echo "Compiling source code..."
find "$SRC_DIR" -name "*.java" | while read file; do
    relative_path="${file#$SRC_DIR/}"
    echo "  Compiling: $relative_path"
done

javac -d "$CLASSES_DIR" \
    -cp "$CLASSES_DIR:$LIB_DIR/*" \
    $(find "$SRC_DIR" -name "*.java")

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
else
    echo "✗ Compilation failed!"
    exit 1
fi

echo ""
echo "Starting Banking Management System..."
echo "=========================================="
echo ""

# Run the application
cd "$PROJECT_DIR"
java -cp "$CLASSES_DIR:$LIB_DIR/*" com.banking.BankingApp

