# Simple API Test

Write-Host "Testing Authentication Endpoints..." -ForegroundColor Cyan

# Register
Write-Host "`n1. Registering user 'testuser'..." -ForegroundColor Yellow
$registerJson = @"
{
  "userName": "testuser",
  "password": "pass123",
  "userFname": "Test",
  "userLname": "User",
  "userContactNumber": 9999999999,
  "userEmail": "test@test.com",
  "userAddress": "Test Address"
}
"@

try {
    $result = Invoke-WebRequest -Uri "http://localhost:8081/auth/register" `
        -Method Post `
        -Body $registerJson `
        -ContentType "application/json" `
        -UseBasicParsing
    Write-Host "Registration Status: $($result.StatusCode)" -ForegroundColor Green
    Write-Host $result.Content
} catch {
    Write-Host "Registration Response: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Yellow
    Write-Host $_.Exception.Message
}

# Login
Write-Host "`n2. Logging in as 'testuser'..." -ForegroundColor Yellow
$loginJson = @"
{
  "username": "testuser",
  "password": "pass123"
}
"@

try {
    $result = Invoke-WebRequest -Uri "http://localhost:8081/auth/login" `
        -Method Post `
        -Body $loginJson `
        -ContentType "application/json" `
        -UseBasicParsing
    Write-Host "Login Status: $($result.StatusCode)" -ForegroundColor Green
    $response = $result.Content | ConvertFrom-Json
    Write-Host "JWT Token: $($response.jwtToken.Substring(0,40))..." -ForegroundColor Green
} catch {
    Write-Host "Login Failed: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Red
    if ($_.ErrorDetails) {
        Write-Host "Error Details: $($_.ErrorDetails.Message)" -ForegroundColor Red
    }
    Write-Host "Full Error: $($_.Exception.Message)" -ForegroundColor Red
}
