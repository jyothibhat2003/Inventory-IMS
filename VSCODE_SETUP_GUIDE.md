# 📘 Complete VS Code Setup & Run Guide

## 🎯 From Scratch to Running Application

This guide will take you from a fresh setup to a fully running Inventory Management System in VS Code.

---

## 📋 Prerequisites

### 1. Install Required Software

#### A. Java Development Kit (JDK) 21+
- Download: https://adoptium.net/ (Eclipse Temurin)
- Install Java 21 or higher
- Verify: `java -version` (should show 21 or higher)

#### B. Docker Desktop
- Download: https://www.docker.com/products/docker-desktop
- Install and start Docker Desktop
- Verify: `docker --version`

#### C. Visual Studio Code
- Download: https://code.visualstudio.com/
- Install VS Code

#### D. Maven (Optional - included in project)
- The project includes Maven Wrapper (`mvnw.cmd`)
- No separate Maven installation needed

---

## 🔧 VS Code Setup

### Step 1: Install Required Extensions

Open VS Code and install these extensions:

1. **Extension Pack for Java** (by Microsoft)
   - Includes Java language support, debugging, testing
   - Press `Ctrl+Shift+X` to open Extensions
   - Search for "Extension Pack for Java"
   - Click Install

2. **Spring Boot Extension Pack** (by VMware)
   - Spring Boot support and tools
   - Search for "Spring Boot Extension Pack"
   - Click Install

3. **Docker** (by Microsoft) - Optional but helpful
   - Search for "Docker"
   - Click Install

### Step 2: Open the Project in VS Code

1. Open VS Code
2. Click **File → Open Folder**
3. Navigate to: `C:\Users\Admin\Downloads\InventoryManagementSystem-SpringBoot`
4. Click **Select Folder**

VS Code will automatically:
- Detect it's a Maven project
- Start importing dependencies
- Configure Java environment

**Wait for the import to complete** (check bottom right corner for progress)

---

## 🐳 Step 3: Start MySQL Database

### Option A: Using Docker Desktop (Recommended)

1. **Open Terminal in VS Code**: Press `` Ctrl+` `` (backtick)

2. **Run MySQL Container**:
```powershell
docker run -d --name mysql-db -e MYSQL_DATABASE=wv -e MYSQL_ROOT_PASSWORD=egxduvwz -p 3307:3306 mysql:8.0
```

3. **Verify it's running**:
```powershell
docker ps
```

You should see `mysql-db` container running.

### Option B: Using Docker Compose

```powershell
docker compose up -d db
```

---

## 🏗️ Step 4: Build the Project

### Option A: Using VS Code Terminal

1. Open Terminal in VS Code: `` Ctrl+` ``

2. Run Maven build:
```powershell
.\mvnw.cmd clean package -DskipTests
```

Wait for "BUILD SUCCESS" message.

### Option B: Using VS Code Maven Extension

1. Click on **Maven** icon in the sidebar (if Extension Pack is installed)
2. Expand your project
3. Expand **Lifecycle**
4. Right-click on **package** → Run

---

## 🚀 Step 5: Run the Application

### Method 1: Using the Run Script (Easiest)

In VS Code Terminal:

```powershell
powershell -ExecutionPolicy Bypass -File run-app.ps1
```

### Method 2: Using VS Code Run Configuration

1. **Create Launch Configuration**:
   - Click **Run and Debug** icon (or press `Ctrl+Shift+D`)
   - Click **create a launch.json file**
   - Select **Java**
   - Choose **Launch Current File**

2. **Update launch.json**:

Open `.vscode/launch.json` and replace with:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Launch Inventory App",
            "request": "launch",
            "mainClass": "com.example.DataJpaApplication",
            "projectName": "data-jpa",
            "env": {
                "DB_HOST": "localhost",
                "DB_PORT": "3307",
                "DB_NAME": "wv",
                "DB_USER": "root",
                "DB_PASS": "egxduvwz",
                "SERVER_PORT": "8081"
            }
        }
    ]
}
```

3. **Run the application**:
   - Press `F5` or click the green play button
   - Select "Launch Inventory App"

### Method 3: Using Maven Spring Boot Plugin

In Terminal:

```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="3307"
$env:DB_NAME="wv"
$env:DB_USER="root"
$env:DB_PASS="egxduvwz"
$env:SERVER_PORT="8081"
.\mvnw.cmd spring-boot:run
```

---

## ✅ Step 6: Verify Application is Running

### Check the Logs

In the terminal/debug console, you should see:

```
Started DataJpaApplication in XX.XXX seconds
Tomcat started on port 8081 (http) with context path '/'
```

### Test the Home Page

1. **Open Browser**: Press `Ctrl+Click` on the URL in VS Code, or manually open:
```
http://localhost:8081
```

You should see the beautiful Inventory Management System home page!

### Test the API

Open a new terminal in VS Code and run:

```powershell
# Test registration endpoint
$body = @{
    userName = "admin"
    password = "admin123"
    userFname = "Admin"
    userLname = "User"
    userContactNumber = 1234567890
    userEmail = "admin@example.com"
    userAddress = "123 Main St"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/auth/register" -Method Post -Body $body -ContentType "application/json"
```

---

## 🎨 Using the Application

### 1. Access the Home Page

```
http://localhost:8081
```

Features:
- System status indicator
- Feature overview
- API endpoint documentation
- Responsive design

### 2. Register a User (API)

**Endpoint**: `POST /auth/register`

**PowerShell**:
```powershell
$user = @{
    userName = "john"
    password = "pass123"
    userFname = "John"
    userLname = "Doe"
    userContactNumber = 9876543210
    userEmail = "john@example.com"
    userAddress = "456 Oak Street"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/auth/register" -Method Post -Body $user -ContentType "application/json"
```

**cURL**:
```bash
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"userName\":\"john\",\"password\":\"pass123\",\"userFname\":\"John\",\"userLname\":\"Doe\",\"userContactNumber\":9876543210,\"userEmail\":\"john@example.com\",\"userAddress\":\"456 Oak Street\"}"
```

### 3. Login and Get JWT Token

**PowerShell**:
```powershell
$login = @{
    username = "john"
    password = "pass123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8081/auth/login" -Method Post -Body $login -ContentType "application/json"
$token = $response.jwtToken
Write-Host "Your Token: $token"
```

### 4. Use Protected Endpoints

**Create a Category**:
```powershell
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

$category = @{
    categoryName = "Electronics"
    createdUser = "john"
    createdDateTime = (Get-Date -Format "yyyy-MM-ddTHH:mm:ss")
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Post -Body $category -Headers $headers
```

**Get All Categories**:
```powershell
$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Get -Headers $headers
```

**Create a Product**:
```powershell
$product = @{
    productName = "Laptop"
    productIsService = $false
    productbuyingPrice = 500.00
    productsellingPrice = 800.00
    createdUser = "john"
    createdDateTime = (Get-Date -Format "yyyy-MM-ddTHH:mm:ss")
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/api/products" -Method Post -Body $product -Headers $headers
```

### 5. Use Testing Scripts

The project includes ready-made test scripts:

```powershell
# Quick authentication test
powershell -ExecutionPolicy Bypass -File test-simple.ps1

# Full API test
powershell -ExecutionPolicy Bypass -File test-api.ps1
```

---

## 🔍 Debugging in VS Code

### Set Breakpoints

1. Open any Java file (e.g., `HomeController.java`)
2. Click in the left margin next to line numbers to set breakpoints (red dot appears)
3. Press `F5` to start debugging
4. Access the endpoint in browser/Postman
5. VS Code will pause at your breakpoint

### Use Debug Console

- View variables
- Evaluate expressions
- Step through code (F10 = step over, F11 = step into)

---

## 🛠️ Development Workflow in VS Code

### 1. Making Changes to Java Code

1. **Edit Code**:
   - Open any `.java` file
   - Make your changes
   - VS Code auto-saves

2. **Rebuild**:
   - In terminal: `.\mvnw.cmd compile`
   - Or use Maven sidebar

3. **Restart Application**:
   - Stop current run (Ctrl+C or Stop button)
   - Press F5 to restart

### 2. Making Changes to HTML Templates

1. **Edit Template**:
   - Open `src/main/resources/templates/index.html`
   - Make changes

2. **Rebuild**:
   ```powershell
   .\mvnw.cmd compile
   ```

3. **Restart Application**

4. **Refresh Browser**: `Ctrl+F5` (hard refresh)

### 3. Hot Reload (Optional)

Add Spring Boot DevTools for automatic restarts:

In `pom.xml`, add:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

Then rebuild and restart.

---

## 📊 VS Code Useful Features

### 1. Explorer Sidebar (`Ctrl+Shift+E`)
- Browse project files
- Create new files/folders
- Rename/delete files

### 2. Search (`Ctrl+Shift+F`)
- Search across entire project
- Find and replace
- Regex support

### 3. Source Control (`Ctrl+Shift+G`)
- View Git changes
- Commit changes
- Push/pull from repository

### 4. Terminal (`` Ctrl+` ``)
- Integrated PowerShell terminal
- Run commands without leaving VS Code
- Split terminals

### 5. Problems Panel (`Ctrl+Shift+M`)
- View compilation errors
- See warnings
- Quick fix suggestions

### 6. Extensions (`Ctrl+Shift+X`)
- Install additional extensions
- Manage installed extensions

---

## 🎯 Complete Project Structure in VS Code

```
InventoryManagementSystem-SpringBoot/
├── .vscode/                          # VS Code configuration
│   └── launch.json                   # Debug configuration
├── src/
│   └── main/
│       ├── java/com/example/
│       │   ├── controller/           # REST & MVC Controllers
│       │   │   ├── HomeController.java (Web page)
│       │   │   ├── AuthController.java (Login/Register)
│       │   │   ├── CategoryController.java
│       │   │   ├── ProductController.java
│       │   │   └── ... (9 controllers total)
│       │   ├── entity/               # Database entities
│       │   ├── repository/           # Data access
│       │   ├── service/              # Business logic
│       │   ├── security/             # JWT & Security config
│       │   └── DataJpaApplication.java (Main class)
│       └── resources/
│           ├── templates/            # Thymeleaf templates
│           │   └── index.html        # Home page
│           └── application.properties # Configuration
├── target/                           # Compiled files (auto-generated)
├── pom.xml                           # Maven configuration
├── run-app.ps1                       # Quick start script
├── test-simple.ps1                   # API test script
└── VSCODE_SETUP_GUIDE.md            # This file
```

---

## 🚀 Quick Commands Reference

### In VS Code Terminal

```powershell
# Build project
.\mvnw.cmd clean package -DskipTests

# Run application
powershell -ExecutionPolicy Bypass -File run-app.ps1

# Test API
powershell -ExecutionPolicy Bypass -File test-simple.ps1

# Stop running app
Stop-Process -Name "java" -Force

# Check if app is running
Test-NetConnection -ComputerName localhost -Port 8081

# View Docker containers
docker ps

# Stop MySQL
docker stop mysql-db

# Start MySQL
docker start mysql-db

# View MySQL logs
docker logs mysql-db

# Access MySQL CLI
docker exec -it mysql-db mysql -uroot -pegxduvwz wv
```

---

## 🎓 Learning Resources

### Understanding the Code

1. **Controllers** (`src/main/java/com/example/controller/`)
   - Handle HTTP requests
   - Return JSON responses or views
   - Use `@RestController` for API, `@Controller` for web pages

2. **Services** (`src/main/java/com/example/service/`)
   - Business logic layer
   - Called by controllers
   - Use repositories to access data

3. **Repositories** (`src/main/java/com/example/repository/`)
   - Data access layer
   - Extend `JpaRepository`
   - Auto-generate database queries

4. **Entities** (`src/main/java/com/example/entity/`)
   - Represent database tables
   - Use JPA annotations
   - Define relationships

5. **Security** (`src/main/java/com/example/security/`)
   - JWT token generation
   - Authentication filter
   - Security configuration

### Spring Boot Documentation
- https://spring.io/projects/spring-boot
- https://docs.spring.io/spring-boot/docs/current/reference/html/

### Thymeleaf Documentation
- https://www.thymeleaf.org/documentation.html

---

## ❓ Troubleshooting

### Issue: Port 8081 already in use

```powershell
# Find process using port 8081
netstat -ano | findstr :8081

# Kill the process (replace PID)
taskkill /PID <process_id> /F

# Or change port in run-app.ps1
$env:SERVER_PORT="8082"
```

### Issue: MySQL connection failed

```powershell
# Check if MySQL container is running
docker ps

# If not running, start it
docker start mysql-db

# If doesn't exist, create it
docker run -d --name mysql-db -e MYSQL_DATABASE=wv -e MYSQL_ROOT_PASSWORD=egxduvwz -p 3307:3306 mysql:8.0
```

### Issue: Build fails

```powershell
# Clean and rebuild
.\mvnw.cmd clean package -DskipTests

# If still fails, delete target folder and rebuild
Remove-Item -Recurse -Force target
.\mvnw.cmd package -DskipTests
```

### Issue: Java not found

```powershell
# Check Java installation
java -version

# If not installed, download from https://adoptium.net/
```

### Issue: VS Code not recognizing Java project

1. Press `Ctrl+Shift+P`
2. Type "Java: Clean Java Language Server Workspace"
3. Select it and reload VS Code
4. Wait for project to reimport

---

## ✅ Verification Checklist

Before starting development, verify:

- [ ] Java 21+ installed (`java -version`)
- [ ] Docker Desktop running
- [ ] VS Code Java extensions installed
- [ ] MySQL container running (`docker ps`)
- [ ] Project builds successfully (`.\mvnw.cmd package -DskipTests`)
- [ ] Application starts (`powershell -File run-app.ps1`)
- [ ] Home page accessible (`http://localhost:8081`)
- [ ] API endpoints working (run `test-simple.ps1`)

---

## 🎉 You're Ready!

Your development environment is now fully configured!

**Next Steps:**
1. Start MySQL: `docker start mysql-db`
2. Open project in VS Code
3. Press `F5` to run with debugging
4. Open `http://localhost:8081` in browser
5. Start developing!

**Happy Coding! 🚀**
