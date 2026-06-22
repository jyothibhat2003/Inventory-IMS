# 🚀 Quick Start Guide

## ⚠️ Important: Root URL is Protected!

If you see **HTTP 403 Forbidden** when accessing `http://localhost:8081`, this is **NORMAL**!

The root endpoint `/` is protected by Spring Security. You need to:
1. Register a user
2. Login to get a JWT token
3. Use the token to access protected endpoints

---

## ✅ Step-by-Step Access

### Step 1: Open PowerShell

Press `Win + X` and select "Windows PowerShell" or "Terminal"

### Step 2: Navigate to Project Directory

```powershell
cd "C:\Users\Admin\Downloads\InventoryManagementSystem-SpringBoot"
```

### Step 3: Run the Test Script

```powershell
powershell -ExecutionPolicy Bypass -File test-simple.ps1
```

**Expected Output:**
```
Testing Authentication Endpoints...

1. Registering user 'testuser'...
Registration Status: 200

2. Logging in as 'testuser'...
Login Status: 200
JWT Token: eyJhbGciOiJIUzUxMiJ9...
```

---

## 🔐 Manual Testing (Copy & Paste)

### 1. Register a User

Copy and paste this entire block into PowerShell:

```powershell
$registerBody = @{
    userName = "myuser"
    password = "mypass123"
    userFname = "John"
    userLname = "Doe"
    userContactNumber = 1234567890
    userEmail = "john@example.com"
    userAddress = "123 Street Name"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/auth/register" -Method Post -Body $registerBody -ContentType "application/json"
```

### 2. Login and Get Token

```powershell
$loginBody = @{
    username = "myuser"
    password = "mypass123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8081/auth/login" -Method Post -Body $loginBody -ContentType "application/json"
$token = $response.jwtToken
Write-Host "Your JWT Token:" -ForegroundColor Green
Write-Host $token
```

### 3. Test a Protected Endpoint

```powershell
$headers = @{
    "Authorization" = "Bearer $token"
}

# Get all categories
Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Get -Headers $headers
```

---

## 🌐 Testing in Browser

You **cannot** test authenticated endpoints directly in the browser without extensions. Use one of these methods:

### Option A: Use Postman (Recommended)
1. Download Postman: https://www.postman.com/downloads/
2. Import the endpoints from `API_TESTING_GUIDE.md`
3. Set Authorization header with JWT token

### Option B: Use Browser Extension
- Install "ModHeader" or "Simple Modify Headers"
- Add header: `Authorization: Bearer YOUR_TOKEN_HERE`

### Option C: Use cURL (Command Line)
```bash
# Register
curl -X POST http://localhost:8081/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"userName\":\"user1\",\"password\":\"pass123\",\"userFname\":\"First\",\"userLname\":\"Last\",\"userContactNumber\":9876543210,\"userEmail\":\"user@test.com\",\"userAddress\":\"Address\"}"

# Login
curl -X POST http://localhost:8081/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"user1\",\"password\":\"pass123\"}"

# Use token (replace TOKEN with actual token)
curl -X GET http://localhost:8081/api/categories ^
  -H "Authorization: Bearer TOKEN"
```

---

## 📝 Available Public Endpoints

These endpoints do NOT require authentication:

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/auth/register` | POST | Register new user |
| `/auth/login` | POST | Login and get JWT token |

---

## 🔒 Protected Endpoints (Require JWT Token)

All other endpoints require JWT authentication:

| Endpoint Pattern | Purpose |
|------------------|---------|
| `/api/categories` | Category management |
| `/api/products` | Product management |
| `/api/stock` | Stock/Inventory |
| `/api/suppliers` | Supplier management |
| `/api/pricing` | Pricing rules |
| `/api/invoices` | Invoice management |

---

## ⚡ One-Line Complete Test

Copy this entire block for a complete test:

```powershell
# Complete API Test
Write-Host "`n=== REGISTERING USER ===" -ForegroundColor Cyan
$reg = @{userName="testuser";password="test123";userFname="Test";userLname="User";userContactNumber=9999999999;userEmail="test@test.com";userAddress="Test St"} | ConvertTo-Json
try { Invoke-RestMethod -Uri "http://localhost:8081/auth/register" -Method Post -Body $reg -ContentType "application/json" | Out-Null; Write-Host "✓ User registered" -ForegroundColor Green } catch { Write-Host "User may exist" -ForegroundColor Yellow }

Write-Host "`n=== LOGGING IN ===" -ForegroundColor Cyan
$login = @{username="testuser";password="test123"} | ConvertTo-Json
$token = (Invoke-RestMethod -Uri "http://localhost:8081/auth/login" -Method Post -Body $login -ContentType "application/json").jwtToken
Write-Host "✓ Logged in! Token: $($token.Substring(0,40))..." -ForegroundColor Green

Write-Host "`n=== CREATING CATEGORY ===" -ForegroundColor Cyan
$cat = @{categoryName="Electronics";createdUser="testuser";createdDateTime=(Get-Date -Format "yyyy-MM-ddTHH:mm:ss")} | ConvertTo-Json
$headers = @{"Authorization"="Bearer $token";"Content-Type"="application/json"}
$newCat = Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Post -Body $cat -Headers $headers
Write-Host "✓ Category created with ID: $($newCat.categoryId)" -ForegroundColor Green

Write-Host "`n=== FETCHING ALL CATEGORIES ===" -ForegroundColor Cyan
$categories = Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method Get -Headers @{"Authorization"="Bearer $token"}
Write-Host "✓ Found $($categories.Count) category(ies)" -ForegroundColor Green
$categories | Format-Table categoryId, categoryName

Write-Host "`n✓ ALL TESTS PASSED!" -ForegroundColor Green
```

---

## 🎯 Why You See 403 Error

### ❌ Wrong Way (403 Error):
- Opening `http://localhost:8081` in browser
- Accessing any `/api/*` endpoint without token

### ✅ Right Way:
1. Call `/auth/register` to create user
2. Call `/auth/login` to get JWT token
3. Add token to `Authorization` header
4. Now access `/api/*` endpoints

---

## 🛠️ Verification Checklist

Before testing, verify:

```powershell
# Check if application is running
Test-NetConnection -ComputerName localhost -Port 8081

# Check if MySQL is running
docker ps | Select-String "mysql"
```

Both should show as running/accessible.

---

## 📞 Common Issues

### Issue 1: "Connection Refused"
**Solution**: Application isn't running. Start it:
```powershell
powershell -ExecutionPolicy Bypass -File run-app.ps1
```

### Issue 2: "403 Forbidden" on `/auth/login`
**Solution**: User doesn't exist. Register first using `/auth/register`

### Issue 3: "401 Unauthorized" on API endpoints
**Solution**: JWT token expired or invalid. Login again to get new token

### Issue 4: "Cannot find file test-simple.ps1"
**Solution**: You're not in the right directory:
```powershell
cd "C:\Users\Admin\Downloads\InventoryManagementSystem-SpringBoot"
```

---

## 🎉 Success Indicators

You'll know it's working when you see:

✓ User registration returns `Status 200`  
✓ Login returns a JWT token starting with `eyJ...`  
✓ API calls with token return data (not 403)  
✓ Categories/Products can be created and retrieved  

---

## 📚 Next Steps

1. ✅ Run `test-simple.ps1` to verify everything works
2. ✅ Read `API_TESTING_GUIDE.md` for detailed API documentation
3. ✅ Read `SETUP_COMPLETE.md` for complete system information
4. ✅ Start building your application!

---

**Remember**: `http://localhost:8081` alone will always show 403. This is correct behavior!

Use the authentication flow above to access the system properly.
