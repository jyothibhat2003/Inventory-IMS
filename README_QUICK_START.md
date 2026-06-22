# 🚀 Quick Start Guide - 5 Minutes to Running

## ✅ Current Status: READY TO USE!

The application is **already configured and running**! Just follow these steps to use it in VS Code.

---

## 🎯 Super Quick Start (1 Minute)

### Step 1: Open in VS Code
```
1. Open VS Code
2. File → Open Folder
3. Select: C:\Users\Admin\Downloads\InventoryManagementSystem-SpringBoot
```

### Step 2: View the Home Page
```
Open browser: http://localhost:8081
```

**That's it! The application is already running!**

---

## 📝 What's Already Done

✅ MySQL database running in Docker (port 3307)  
✅ Application built and compiled  
✅ Server running on port 8081  
✅ Home page accessible  
✅ All API endpoints ready  
✅ VS Code configuration files created  

---

## 🎨 Using the Application

### 1. Access Home Page
```
http://localhost:8081
```

### 2. Test the API

Open VS Code Terminal (Ctrl+`) and run:

```powershell
# Quick test
powershell -ExecutionPolicy Bypass -File test-simple.ps1
```

**OR** Register and Login manually:

```powershell
# Register
$user = @{
    userName = "admin"
    password = "admin123"
    userFname = "Admin"
    userLname = "User"
    userContactNumber = 1234567890
    userEmail = "admin@example.com"
    userAddress = "123 Main St"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/auth/register" -Method Post -Body $user -ContentType "application/json"

# Login
$login = @{
    username = "admin"
    password = "admin123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8081/auth/login" -Method Post -Body $login -ContentType "application/json"
$token = $response.jwtToken
Write-Host "Token: $token"
```

### 3. Use Protected Endpoints

```powershell
# Create Category
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

$category = @{
    categoryName = "Electronics"
    createdUser = "admin"
    createdDateTime = (Get-Date -Format "yyyy-MM-ddTHH:mm:ss")
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Post -Body $category -Headers $headers
```

---

## 🛠️ Common Tasks

### Stop the Application
```powershell
Stop-Process -Name "java" -Force
```

### Start the Application
```powershell
powershell -ExecutionPolicy Bypass -File run-app.ps1
```

### Restart After Code Changes
```powershell
# 1. Stop
Stop-Process -Name "java" -Force

# 2. Rebuild
.\mvnw.cmd compile

# 3. Restart
powershell -ExecutionPolicy Bypass -File run-app.ps1
```

### Debug in VS Code
```
1. Press F5
2. Select "Launch Inventory Management System"
3. Set breakpoints by clicking left of line numbers
```

---

## 📚 Available Resources

| Resource | Purpose | Location |
|----------|---------|----------|
| **Home Page** | Web interface | http://localhost:8081 |
| **API Docs** | Endpoint reference | API_TESTING_GUIDE.md |
| **Setup Guide** | VS Code detailed setup | VSCODE_SETUP_GUIDE.md |
| **Test Scripts** | API testing | test-simple.ps1, test-api.ps1 |
| **Launch Config** | VS Code debugging | .vscode/launch.json |

---

## 🎯 Project Structure

```
InventoryManagementSystem-SpringBoot/
├── src/main/java/com/example/
│   ├── controller/          # 10 Controllers (API + Web)
│   ├── service/             # 13 Services
│   ├── repository/          # 14 Repositories
│   ├── entity/              # 22 Entities
│   └── security/            # JWT Authentication
├── src/main/resources/
│   ├── templates/
│   │   └── index.html       # Home Page
│   └── application.properties
├── .vscode/
│   ├── launch.json          # Debug configuration
│   └── settings.json        # VS Code settings
├── target/
│   └── data-jpa-0.0.1-SNAPSHOT.jar  # Executable JAR
├── run-app.ps1              # Quick start script
└── test-simple.ps1          # API test script
```

---

## 🔗 API Endpoints

### Public (No Auth Required)
- `POST /auth/register` - Register user
- `POST /auth/login` - Get JWT token
- `GET /` - Home page

### Protected (JWT Required)
- `GET/POST/PUT/DELETE /api/categories`
- `GET/POST/PUT/DELETE /api/products`
- `GET/POST/PUT/DELETE /api/stock`
- `GET/POST/PUT/DELETE /api/suppliers`
- `GET/POST/PUT/DELETE /api/pricing`
- `GET/POST/PUT/DELETE /api/invoices`

---

## 🐛 Troubleshooting

### Can't access http://localhost:8081

**Check if app is running:**
```powershell
Test-NetConnection -ComputerName localhost -Port 8081
```

If False, start the app:
```powershell
powershell -ExecutionPolicy Bypass -File run-app.ps1
```

### Port 8081 already in use

```powershell
# Find process
netstat -ano | findstr :8081

# Kill it
taskkill /PID <process_id> /F
```

### MySQL not running

```powershell
# Check MySQL
docker ps

# Start if stopped
docker start mysql-db

# Create if doesn't exist
docker run -d --name mysql-db -e MYSQL_DATABASE=wv -e MYSQL_ROOT_PASSWORD=egxduvwz -p 3307:3306 mysql:8.0
```

---

## ⚡ One-Line Complete Setup

If you need to setup from scratch:

```powershell
docker run -d --name mysql-db -e MYSQL_DATABASE=wv -e MYSQL_ROOT_PASSWORD=egxduvwz -p 3307:3306 mysql:8.0; Start-Sleep -Seconds 10; .\mvnw.cmd clean package -DskipTests; powershell -ExecutionPolicy Bypass -File run-app.ps1
```

Then open: http://localhost:8081

---

## 📖 Next Steps

1. ✅ **Explore the Home Page**: http://localhost:8081
2. ✅ **Run Test Scripts**: `test-simple.ps1`
3. ✅ **Read API Guide**: `API_TESTING_GUIDE.md`
4. ✅ **Try Debugging**: Press F5 in VS Code
5. ✅ **Modify Code**: Edit controllers and restart

---

## 💡 Pro Tips

### Use VS Code Integrated Terminal
- Press `` Ctrl+` `` to open terminal
- Run commands without leaving VS Code
- Multiple terminals available

### Use Maven Sidebar
- Click Maven icon in VS Code
- Run builds, clean, package visually
- View project dependencies

### Use Breakpoints
- Click left of line number to add breakpoint
- Press F5 to start debugging
- View variables in Debug panel

### Auto Format Code
- Right-click in editor
- Select "Format Document"
- Or press `Shift+Alt+F`

---

## 🎉 You're All Set!

The system is running and ready to use. Open VS Code and start developing!

**Current Status:**
- ✅ MySQL: Running on port 3307
- ✅ Application: Running on port 8081
- ✅ Home Page: http://localhost:8081
- ✅ API: Ready for requests

**Happy Coding! 🚀**
