# 🚀 VotingBank Simple Demo Guide

Follow these steps to test all functionalities of the VotingBank application.

---

## ✅ Prerequisites

- Application running on: `http://localhost:8081`
- Open PowerShell or Command Prompt

---

## 📝 Step-by-Step Demo

### Step 1: Register User 1

```powershell
$body = '{"usernameOrEmail":"alice@example.com","password":"Pass123"}'
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" -Method POST -ContentType "application/json" -Body $body
$token1 = $response.token
Write-Host "Token 1: $token1"
```

**Expected Output**: User registered successfully with a JWT token

---

### Step 2: Register User 2

```powershell
$body = '{"usernameOrEmail":"bob@example.com","password":"Pass123"}'
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" -Method POST -ContentType "application/json" -Body $body
$token2 = $response.token
Write-Host "Token 2: $token2"
```

---

### Step 3: Login Test

```powershell
$body = '{"usernameOrEmail":"alice@example.com","password":"Pass123"}'
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/login" -Method POST -ContentType "application/json" -Body $body
Write-Host "Login successful! Role: $($response.role)"
```

---

### Step 4: Update User Profile

```powershell
$body = '{"fullName":"Alice Johnson","department":"Computer Science","rollNo":"CS2021001"}'
$headers = @{"Authorization"="Bearer $token1"; "Content-Type"="application/json"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/users/profile" -Method PUT -Headers $headers -Body $body
Write-Host "Profile updated!"
```

---

### Step 5: Get User Profile

```powershell
$headers = @{"Authorization"="Bearer $token1"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/users/profile" -Method GET -Headers $headers
$response | ConvertTo-Json
```

---

## 🔐 Admin Functions (Requires Admin Role)

### Make User Admin (Using H2 Console)

1. Open: `http://localhost:8081/h2-console`
2. Connect with:
   - JDBC URL: `jdbc:h2:mem:votingbank`
   - Username: `sa`
   - Password: (leave empty)
3. Run SQL:
```sql
UPDATE USERS SET role_id = 2 WHERE email = 'alice@example.com';
```
4. Click "Run"
5. Re-login to get new token with admin role

---

### Step 6: Create Election (Admin)

```powershell
$body = '{"title":"Student Election 2024","description":"Annual election","startAt":"2024-12-13T00:00:00Z","endAt":"2024-12-20T23:59:59Z"}'
$headers = @{"Authorization"="Bearer $token1"; "Content-Type"="application/json"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/elections" -Method POST -Headers $headers -Body $body
$electionId = $response.id
Write-Host "Election ID: $electionId"
```

---

### Step 7: Add Candidate 1 (Admin)

```powershell
$body = '{"name":"Alice Johnson","description":"CS student"}'
$headers = @{"Authorization"="Bearer $token1"; "Content-Type"="application/json"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/candidates" -Method POST -Headers $headers -Body $body
$candidateId1 = $response.id
Write-Host "Candidate 1 ID: $candidateId1"
```

---

### Step 8: Add Candidate 2 (Admin)

```powershell
$body = '{"name":"Bob Smith","description":"Engineering student"}'
$headers = @{"Authorization"="Bearer $token1"; "Content-Type"="application/json"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/candidates" -Method POST -Headers $headers -Body $body
$candidateId2 = $response.id
Write-Host "Candidate 2 ID: $candidateId2"
```

---

### Step 9: Publish Election (Admin)

```powershell
$headers = @{"Authorization"="Bearer $token1"}
Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/publish" -Method PUT -Headers $headers
Write-Host "Election published!"
```

---

### Step 10: Get All Elections

```powershell
$headers = @{"Authorization"="Bearer $token1"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/elections" -Method GET -Headers $headers
$response | ConvertTo-Json
```

---

### Step 11: Get Candidates

```powershell
$headers = @{"Authorization"="Bearer $token1"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/candidates" -Method GET -Headers $headers
$response | ConvertTo-Json
```

---

### Step 12: Cast Vote (User 1)

```powershell
$body = "{`"electionId`":`"$electionId`",`"candidateId`":`"$candidateId1`"}"
$headers = @{"Authorization"="Bearer $token1"; "Content-Type"="application/json"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/votes" -Method POST -Headers $headers -Body $body
Write-Host "Vote cast successfully!"
```

---

### Step 13: Cast Vote (User 2)

```powershell
$body = "{`"electionId`":`"$electionId`",`"candidateId`":`"$candidateId2`"}"
$headers = @{"Authorization"="Bearer $token2"; "Content-Type"="application/json"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/votes" -Method POST -Headers $headers -Body $body
Write-Host "Vote cast successfully!"
```

---

### Step 14: Get Vote History

```powershell
$headers = @{"Authorization"="Bearer $token1"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/votes/history" -Method GET -Headers $headers
$response | ConvertTo-Json
```

---

### Step 15: Get Election Results

```powershell
$headers = @{"Authorization"="Bearer $token1"}
$response = Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/results" -Method GET -Headers $headers
$response | ConvertTo-Json
```

---

## 🌐 Using the Web UI

1. Open browser: `http://localhost:8081`
2. You'll see 12 sections for different operations
3. Use the tokens from above in the "JWT Token" fields
4. Fill in the required fields
5. Click the buttons to test each functionality

---

## 📊 View Database (H2 Console)

1. Open: `http://localhost:8081/h2-console`
2. Connect:
   - JDBC URL: `jdbc:h2:mem:votingbank`
   - Username: `sa`
   - Password: (empty)
3. Run queries:

```sql
-- View all users
SELECT * FROM USERS;

-- View all elections
SELECT * FROM ELECTIONS;

-- View all candidates
SELECT * FROM CANDIDATES;

-- View all votes
SELECT * FROM VOTES;

-- View vote counts
SELECT c.name, COUNT(v.id) as votes
FROM CANDIDATES c
LEFT JOIN VOTES v ON c.id = v.candidate_id
GROUP BY c.id, c.name;
```

---

## 🎯 Quick Test Commands (Copy & Paste)

### Complete Test Flow

```powershell
# Register users
$u1 = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" -Method POST -ContentType "application/json" -Body '{"usernameOrEmail":"test1@test.com","password":"Pass123"}'
$u2 = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" -Method POST -ContentType "application/json" -Body '{"usernameOrEmail":"test2@test.com","password":"Pass123"}'

Write-Host "User 1 Token: $($u1.token)"
Write-Host "User 2 Token: $($u2.token)"
Write-Host "Now make test1@test.com an admin in H2 Console, then continue..."
```

---

## 📚 Additional Resources

- **Full API Documentation**: See `API_USAGE_GUIDE.md`
- **Database Setup**: See `DATABASE_SETUP.md`
- **Project README**: See `README.md`

---

## ✅ Success Indicators

- ✓ User registration returns JWT token
- ✓ Login works with registered credentials
- ✓ Profile can be updated and retrieved
- ✓ Elections can be created (admin only)
- ✓ Candidates can be added (admin only)
- ✓ Users can cast votes
- ✓ Results show vote counts
- ✓ Vote history is tracked

---

**Happy Testing! 🎉**
