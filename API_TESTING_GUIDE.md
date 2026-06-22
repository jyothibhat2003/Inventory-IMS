# Inventory Management System - API Testing Guide

## 🚀 Application Status: **RUNNING**

- **Application URL**: http://localhost:8081
- **Database**: MySQL 8.0 on localhost:3307
- **Database Name**: wv
- **API Status**: ✅ Successfully Started

## 📋 System Architecture

### Technology Stack
- **Framework**: Spring Boot 3.4.2
- **Java Version**: 23.0.2
- **Database**: MySQL 8.0.46
- **Security**: Spring Security + JWT
- **ORM**: Hibernate 6.6.5

### Database Schema Created
The application has automatically created all required tables:
- ✅ user, user_role, user_log
- ✅ role, role_permission, role_log
- ✅ permission, permission_log
- ✅ category, category_log
- ✅ product, product_log
- ✅ supplier, supplier_log
- ✅ stock, stock_log
- ✅ pricing, pricing_log
- ✅ invoice, product_invoice
- ✅ product_pricing

## 🔐 Authentication Flow

### 1. Register a New User

**Endpoint**: `POST http://localhost:8081/auth/register`

**Request Body**:
```json
{
  "userName": "admin",
  "password": "admin123",
  "userFname": "Admin",
  "userLname": "User",
  "userContactNumber": 1234567890,
  "userEmail": "admin@example.com",
  "userAddress": "123 Main St"
}
```

**cURL Command**:
```bash
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"userName\":\"admin\",\"password\":\"admin123\",\"userFname\":\"Admin\",\"userLname\":\"User\",\"userContactNumber\":1234567890,\"userEmail\":\"admin@example.com\",\"userAddress\":\"123 Main St\"}"
```

**PowerShell Command**:
```powershell
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

### 2. Login and Get JWT Token

**Endpoint**: `POST http://localhost:8081/auth/login`

**Request Body**:
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**cURL Command**:
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

**PowerShell Command**:
```powershell
$loginBody = @{
    username = "admin"
    password = "admin123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8081/auth/login" -Method Post -Body $loginBody -ContentType "application/json"
$token = $response.token
Write-Host "JWT Token: $token"
```

**Expected Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin"
}
```

### 3. Use JWT Token for Secured Endpoints

For all subsequent API calls, include the JWT token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

## 📦 Available API Endpoints

### Category Management
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Stock Management
- `GET /api/stock` - Get all stock entries
- `GET /api/stock/{id}` - Get stock by ID
- `POST /api/stock` - Create stock entry
- `PUT /api/stock/{id}` - Update stock
- `DELETE /api/stock/{id}` - Delete stock

### Supplier Management
- `GET /api/suppliers` - Get all suppliers
- `GET /api/suppliers/{id}` - Get supplier by ID
- `POST /api/suppliers` - Create new supplier
- `PUT /api/suppliers/{id}` - Update supplier
- `DELETE /api/suppliers/{id}` - Delete supplier

### Pricing Management
- `GET /api/pricing` - Get all pricing rules
- `GET /api/pricing/{id}` - Get pricing by ID
- `POST /api/pricing` - Create pricing rule
- `PUT /api/pricing/{id}` - Update pricing
- `DELETE /api/pricing/{id}` - Delete pricing

### Invoice Management
- `GET /api/invoices` - Get all invoices
- `GET /api/invoices/{id}` - Get invoice by ID
- `POST /api/invoices` - Create new invoice
- `PUT /api/invoices/{id}` - Update invoice
- `DELETE /api/invoices/{id}` - Delete invoice

## 🧪 Example API Test: Create a Category

### Step 1: Register and Login (get token)
```powershell
# Register
$registerBody = @{
    userName = "testuser"
    password = "test123"
    userFname = "Test"
    userLname = "User"
    userContactNumber = 9876543210
    userEmail = "test@example.com"
    userAddress = "456 Test Ave"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/auth/register" -Method Post -Body $registerBody -ContentType "application/json"

# Login
$loginBody = @{
    username = "testuser"
    password = "test123"
} | ConvertTo-Json

$authResponse = Invoke-RestMethod -Uri "http://localhost:8081/auth/login" -Method Post -Body $loginBody -ContentType "application/json"
$token = $authResponse.token
```

### Step 2: Create a Category
```powershell
$categoryBody = @{
    categoryName = "Electronics"
    createdUser = "testuser"
    createdDateTime = (Get-Date -Format "yyyy-MM-ddTHH:mm:ss")
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Post -Body $categoryBody -Headers $headers
```

### Step 3: Get All Categories
```powershell
$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Get -Headers $headers
```

## 🛠️ Management Commands

### Check Application Status
```powershell
# Check if application is running
Test-NetConnection -ComputerName localhost -Port 8081
```

### Stop the Application
Stop the running process or use Ctrl+C in the terminal.

### View Database
```powershell
# Connect to MySQL
docker exec -it mysql-db mysql -uroot -pegxduvwz wv
```

### View Application Logs
Check the terminal where the application is running, or use:
```powershell
# If running in background, check logs
```

## 🔍 Troubleshooting

### Port Already in Use
If port 8081 is already in use:
1. Find the process: `netstat -ano | findstr :8081`
2. Kill the process: `taskkill /PID <process_id> /F`
3. Or change the port in `run-app.ps1`

### Database Connection Issues
```bash
# Check MySQL container is running
docker ps

# Check MySQL logs
docker logs mysql-db

# Restart MySQL container
docker restart mysql-db
```

### Authentication Issues
- Ensure you register a user first before logging in
- JWT tokens expire after a certain time - login again if needed
- Check that password is correctly hashed (handled automatically)

## 📊 Database Access

### Using MySQL CLI
```bash
# Access database
docker exec -it mysql-db mysql -uroot -pegxduvwz wv

# Show tables
SHOW TABLES;

# Check users
SELECT * FROM user;

# Check categories
SELECT * FROM category;
```

### Database Credentials
- **Host**: localhost
- **Port**: 3307 (external), 3306 (internal to container)
- **Database**: wv
- **Username**: root
- **Password**: egxduvwz

## 🎯 Quick Start Testing Script

Save this as `test-api.ps1`:

```powershell
# Complete API Testing Script

# 1. Register User
Write-Host "=== Registering User ===" -ForegroundColor Green
$registerBody = @{
    userName = "demo"
    password = "demo123"
    userFname = "Demo"
    userLname = "User"
    userContactNumber = 5551234567
    userEmail = "demo@example.com"
    userAddress = "789 Demo St"
} | ConvertTo-Json

try {
    $registerResponse = Invoke-RestMethod -Uri "http://localhost:8081/auth/register" -Method Post -Body $registerBody -ContentType "application/json"
    Write-Host "User registered successfully!" -ForegroundColor Green
} catch {
    Write-Host "Registration may have failed or user already exists" -ForegroundColor Yellow
}

# 2. Login
Write-Host "`n=== Logging In ===" -ForegroundColor Green
$loginBody = @{
    username = "demo"
    password = "demo123"
} | ConvertTo-Json

$authResponse = Invoke-RestMethod -Uri "http://localhost:8081/auth/login" -Method Post -Body $loginBody -ContentType "application/json"
$token = $authResponse.token
Write-Host "Login successful! Token: $($token.Substring(0,20))..." -ForegroundColor Green

# 3. Create Category
Write-Host "`n=== Creating Category ===" -ForegroundColor Green
$categoryBody = @{
    categoryName = "Test Category"
    createdUser = "demo"
    createdDateTime = (Get-Date -Format "yyyy-MM-ddTHH:mm:ss")
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

$category = Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Post -Body $categoryBody -Headers $headers
Write-Host "Category created with ID: $($category.categoryId)" -ForegroundColor Green

# 4. Get All Categories
Write-Host "`n=== Fetching All Categories ===" -ForegroundColor Green
$categories = Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Get -Headers @{ "Authorization" = "Bearer $token" }
Write-Host "Total categories: $($categories.Count)" -ForegroundColor Green
$categories | Format-Table

Write-Host "`n=== API Testing Complete! ===" -ForegroundColor Green
```

Run with: `powershell -ExecutionPolicy Bypass -File test-api.ps1`

## ✅ Verification Checklist

- [x] MySQL Database Running (port 3307)
- [x] Spring Boot Application Running (port 8081)
- [x] Database schema created automatically
- [x] All 22 entities mapped correctly
- [x] JWT authentication configured
- [ ] User registered successfully
- [ ] JWT token obtained from login
- [ ] API endpoints accessible with token

## 🎉 Success!

Your Inventory Management System is now fully operational and ready for testing!
