# AutoMatch AI - Development Environment Setup

## System Requirements

### Minimum Requirements
- **OS:** macOS 10.14+, Windows 10+, or Ubuntu 18.04+
- **RAM:** 4GB minimum, 8GB recommended
- **Disk Space:** 3GB free space
- **Internet:** Required for npm/Maven dependencies

### Required Software

| Software | Version | Download |
|----------|---------|----------|
| Java JDK | 11+ | [oracle.com/java](https://www.oracle.com/java/) |
| Maven | 3.6+ | [maven.apache.org](https://maven.apache.org/) |
| Node.js | 14+ | [nodejs.org](https://nodejs.org/) |
| npm | 6+ | Included with Node.js |
| Git | Latest | [git-scm.com](https://git-scm.com/) |
| PostgreSQL | 12+ | [postgresql.org](https://www.postgresql.org/) (Optional for dev) |

## Installation Steps

### Step 1: Verify Java Installation

```bash
# Check Java version
java -version

# Expected output: openjdk version "11" or similar
# If not installed:
# macOS: brew install openjdk@11
# Windows: Download from oracle.com
# Ubuntu: sudo apt-get install openjdk-11-jdk
```

### Step 2: Verify Maven Installation

```bash
# Check Maven version
mvn -version

# Expected output: Apache Maven 3.6.0 or later
# If not installed:
# macOS: brew install maven
# Windows: Download from maven.apache.org
# Ubuntu: sudo apt-get install maven
```

### Step 3: Verify Node.js and npm Installation

```bash
# Check Node version
node -v

# Check npm version
npm -v

# Expected output: v14+
# If not installed: Download from nodejs.org
```

### Step 4: Verify Git Installation

```bash
# Check Git version
git --version

# If not installed:
# macOS: brew install git
# Windows: Download from git-scm.com
# Ubuntu: sudo apt-get install git
```

## Project Setup

### Step 1: Clone Repository

```bash
# Navigate to projects directory
cd ~/Projects

# Clone the repository
git clone https://github.com/faraibekhan/automatch-ai.git

# Navigate into project
cd automatch-ai
```

### Step 2: Backend Setup

```bash
# Navigate to project root
cd /Users/faraibekhan/BankAccount/BankAccount

# Build backend
mvn clean install

# Expected output: BUILD SUCCESS

# Run backend
mvn spring-boot:run

# Expected output:
# Started AutoMatchAiApplication in X seconds
# Application started successfully!
```

**Backend URL:** `http://localhost:8080`

### Step 3: Frontend Setup

```bash
# In a new terminal, navigate to frontend
cd /Users/faraibekhan/BankAccount/BankAccount/frontend

# Install dependencies
npm install

# Expected output: added X packages

# Start frontend
npm start

# Expected output:
# Compiled successfully!
# You can now view frontend in the browser at http://localhost:3000
```

**Frontend URL:** `http://localhost:3000`

## IDE Setup

### IntelliJ IDEA (Recommended for Backend)

1. **Open Project**
   - File → Open
   - Select `/Users/faraibekhan/BankAccount/BankAccount`
   - Click "Open"

2. **Configure JDK**
   - File → Project Structure
   - Select JDK 11+
   - Click "Apply"

3. **Enable Annotation Processing**
   - Preferences → Compiler → Annotation Processors
   - Check "Enable annotation processing"
   - Click "Apply"

4. **Run Backend**
   - Right-click `AutoMatchAiApplication.java`
   - Select "Run"
   - Backend will start on port 8080

### Visual Studio Code (Recommended for Frontend)

1. **Install Extensions**
   - ESLint
   - Prettier
   - Tailwind CSS IntelliSense
   - Thunder Client (for API testing)

2. **Open Frontend Folder**
   - File → Open Folder
   - Select `/Users/faraibekhan/BankAccount/BankAccount/frontend`

3. **Start Development Server**
   - Terminal → New Terminal
   - Run: `npm start`
   - Frontend will start on port 3000

## Environment Configuration

### Backend Configuration File

**Location:** `src/main/resources/application.properties`

**Development Settings (H2 - No Configuration Needed):**
```properties
# Already configured in application.properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
```

**Production Settings (PostgreSQL):**
Create `application-prod.properties`:
```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/automatch_ai
spring.datasource.username=automatch
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect
spring.jpa.hibernate.ddl-auto=validate
```

### Frontend Configuration File

**Location:** `frontend/.env`

```env
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_ENV=development
```

## Verification Checklist

- [ ] Java 11+ installed
- [ ] Maven 3.6+ installed
- [ ] Node.js 14+ installed
- [ ] npm 6+ installed
- [ ] Git installed
- [ ] Backend builds successfully with `mvn clean install`
- [ ] Backend runs successfully with `mvn spring-boot:run`
- [ ] Frontend installs dependencies with `npm install`
- [ ] Frontend runs successfully with `npm start`
- [ ] Browser opens to http://localhost:3000
- [ ] Backend API responds at http://localhost:8080/api
- [ ] IDE configured for development

## Troubleshooting

### Java Issues

**Error: "Java not found"**
```bash
# macOS
brew install openjdk@11
echo 'export PATH="/usr/local/opt/openjdk@11/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Ubuntu
sudo apt-get install openjdk-11-jdk
```

**Error: "Wrong Java version"**
```bash
# Check installed versions
java -version

# Set default (macOS)
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
```

### Maven Issues

**Error: "Maven not found"**
```bash
# macOS
brew install maven

# Ubuntu
sudo apt-get install maven

# Verify
mvn -version
```

**Error: "Build failure - dependencies"**
```bash
# Clear cache and rebuild
mvn clean install -DskipTests
```

### Node Issues

**Error: "Node not found"**
```bash
# Install Node.js
# macOS
brew install node

# Verify
node -v
npm -v
```

**Error: "Port 3000 already in use"**
```bash
# macOS/Linux
lsof -ti:3000 | xargs kill -9

# Windows
netstat -ano | findstr :3000
taskkill /PID <PID> /F
```

### npm Issues

**Error: "npm install fails"**
```bash
# Clear cache
npm cache clean --force

# Reinstall
rm -rf node_modules package-lock.json
npm install
```

**Error: "Module not found"**
```bash
# Reinstall dependencies
cd frontend
npm install

# If specific package:
npm install <package-name>
```

### Backend Issues

**Error: "Port 8080 already in use"**
```bash
# macOS/Linux
lsof -ti:8080 | xargs kill -9

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Or run on different port
mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8081'
```

**Error: "Database connection failed"**
```bash
# Check if using H2 (default, no setup needed)
# If using PostgreSQL, create database:

createdb automatch_ai

# If permission denied:
sudo -u postgres createdb automatch_ai
```

### Frontend Issues

**Error: "CORS errors"**
- Check `.env` file has correct `REACT_APP_API_URL`
- Verify backend is running on port 8080
- Check browser console for exact error

**Error: "API calls 404"**
- Verify backend API endpoints
- Check network tab in DevTools
- Review backend console for errors

## Development Tools

### Postman (API Testing)
```bash
# Install
# Download from: https://www.postman.com/downloads/

# Import collection
# File → Import → Select .json file
```

### Database Browser

**For H2 (Development):**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

**For PostgreSQL (Production):**
```bash
# Install pgAdmin4
# Or use: psql -U automatch -d automatch_ai
```

### Git Commands

```bash
# Check status
git status

# Add changes
git add .

# Commit changes
git commit -m "Description of changes"

# Push to remote
git push origin main

# Pull latest changes
git pull origin main

# Create new branch
git checkout -b feature/new-feature

# Switch branch
git checkout main
```

## IDE Shortcuts (IntelliJ)

| Action | Shortcut |
|--------|----------|
| Run Application | Ctrl+Shift+R or Cmd+Shift+R |
| Debug | Ctrl+Shift+D or Cmd+Shift+D |
| Find Class | Ctrl+O or Cmd+O |
| Search Everywhere | Ctrl+Shift+P or Cmd+Shift+P |
| Format Code | Ctrl+L or Cmd+L |
| Reformat File | Ctrl+Alt+L or Cmd+Alt+L |
| Run Tests | Ctrl+Shift+F10 or Cmd+Shift+R |

## VS Code Shortcuts

| Action | Shortcut |
|--------|----------|
| Command Palette | Cmd+Shift+P |
| Terminal | Ctrl+` |
| Format Document | Shift+Alt+F |
| Debug | F5 |
| Run Task | Cmd+Shift+B |
| Quick Fix | Cmd+. |

## Performance Optimization

### Backend
```bash
# Run with more memory
mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Xmx1024m -Xms512m'
```

### Frontend
```bash
# Build with production optimizations
npm run build

# Analyze bundle size
npm install -g webpack-bundle-analyzer
npm run build -- --stats && webpack-bundle-analyzer
```

## Continuous Development Workflow

### Daily Workflow

```bash
# 1. Start backend in Terminal 1
cd /Users/faraibekhan/BankAccount/BankAccount
mvn spring-boot:run

# 2. Start frontend in Terminal 2
cd /Users/faraibekhan/BankAccount/BankAccount/frontend
npm start

# 3. Open browser to http://localhost:3000
# 4. Make changes and save (auto-reload)
# 5. Check console for errors

# 6. When done, stop servers (Ctrl+C in terminals)
```

### Testing Workflow

```bash
# Backend tests
mvn test

# Frontend tests
npm test

# Test specific file
npm test -- CarCard.test.js
```

## Helpful Resources

### Documentation
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [React Docs](https://react.dev)
- [Tailwind CSS Docs](https://tailwindcss.com)
- [Maven Guide](https://maven.apache.org/guides/)

### Tutorials
- [Spring Boot Tutorial](https://spring.io/guides/gs/spring-boot/)
- [React Tutorial](https://react.dev/learn)
- [REST API Design](https://restfulapi.net/)

### Tools
- [Postman](https://www.postman.com/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Visual Studio Code](https://code.visualstudio.com/)
- [GitHub Desktop](https://desktop.github.com/)

## Next Steps

1. ✅ Complete setup following above steps
2. ✅ Verify both backend and frontend are running
3. ✅ Open Postman and test API endpoints
4. ✅ Create new user account in frontend
5. ✅ Test all features (search, recommendations, favorites)
6. ✅ Review code and documentation
7. ✅ Set up IDE extensions and formatting
8. ✅ Start development on new features

## Support

For setup issues:
1. Check troubleshooting section above
2. Review console error messages
3. Check that all prerequisites are installed
4. Try restarting servers
5. Clear caches and reinstall dependencies

---

**Last Updated:** May 13, 2026
**Version:** 1.0.0
