# 🚀 VotingBank API Usage Guide

Complete guide to using all functionalities of the VotingBank application.

---

## 📋 Table of Contents

1. [Getting Started](#getting-started)
2. [Authentication APIs](#authentication-apis)
3. [Election Management](#election-management)
4. [Candidate Management](#candidate-management)
5. [Voting System](#voting-system)
6. [User Management](#user-management)
7. [Web UI Usage](#web-ui-usage)
8. [Testing with PowerShell](#testing-with-powershell)
9. [Testing with Postman](#testing-with-postman)

---

## 🎯 Getting Started

### Application URLs
- **Base URL**: `http://localhost:8081`
- **API Base**: `http://localhost:8081/api`
- **Web UI**: `http://localhost:8081/index.html`
- **H2 Console**: `http://localhost:8081/h2-console`

### H2 Database Access
```
JDBC URL: jdbc:h2:mem:votingbank
Username: sa
Password: (leave empty)
```

---

## 🔐 Authentication APIs

### 1. Register New User

**Endpoint**: `POST /api/auth/register`

**PowerShell Command**:
```powershell
$body = @{
    usernameOrEmail = "john.doe@example.com"
    password = "SecurePass123"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" `
    -Method POST `
    -ContentType "application/json" `
    -Body $body
```

**Request Body**:
```json
{
    "usernameOrEmail": "john.doe@example.com",
    "password": "SecurePass123"
}
```

**Response**:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "john.doe@example.com",
    "email": "john.doe@example.com",
    "role": "USER"
}
```

---

### 2. Login User

**Endpoint**: `POST /api/auth/login`

**PowerShell Command**:
```powershell
$body = @{
    usernameOrEmail = "john.doe@example.com"
    password = "SecurePass123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/login" `
    -Method POST `
    -ContentType "application/json" `
    -Body $body

# Save token for future requests
$token = $response.token
Write-Host "Token: $token"
```

**Response**:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "john.doe@example.com",
    "email": "john.doe@example.com",
    "role": "USER"
}
```

---

## 🗳️ Election Management

### 3. Create Election (Admin Only)

**Endpoint**: `POST /api/elections`

**PowerShell Command**:
```powershell
# First, login as admin or get token
$token = "YOUR_JWT_TOKEN_HERE"

$body = @{
    title = "Student Council Election 2024"
    description = "Annual student council election"
    startAt = "2024-12-15T09:00:00Z"
    endAt = "2024-12-20T17:00:00Z"
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/elections" `
    -Method POST `
    -Headers $headers `
    -Body $body
```

**Request Body**:
```json
{
    "title": "Student Council Election 2024",
    "description": "Annual student council election",
    "startAt": "2024-12-15T09:00:00Z",
    "endAt": "2024-12-20T17:00:00Z"
}
```

**Response**:
```json
{
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "Student Council Election 2024",
    "description": "Annual student council election",
    "startAt": "2024-12-15T09:00:00Z",
    "endAt": "2024-12-20T17:00:00Z",
    "isPublished": false,
    "isClosed": false,
    "createdAt": "2024-12-13T01:00:00Z",
    "createdBy": {
        "id": "...",
        "username": "admin"
    }
}
```

---

### 4. Get All Elections

**Endpoint**: `GET /api/elections`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/elections" `
    -Method GET `
    -Headers $headers
```

**Response**:
```json
[
    {
        "id": "550e8400-e29b-41d4-a716-446655440000",
        "title": "Student Council Election 2024",
        "description": "Annual student council election",
        "startAt": "2024-12-15T09:00:00Z",
        "endAt": "2024-12-20T17:00:00Z",
        "isPublished": true,
        "isClosed": false
    }
]
```

---

### 5. Get Election by ID

**Endpoint**: `GET /api/elections/{id}`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"
$electionId = "550e8400-e29b-41d4-a716-446655440000"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId" `
    -Method GET `
    -Headers $headers
```

---

### 6. Publish Election (Admin Only)

**Endpoint**: `PUT /api/elections/{id}/publish`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"
$electionId = "550e8400-e29b-41d4-a716-446655440000"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/publish" `
    -Method PUT `
    -Headers $headers
```

---

### 7. Close Election (Admin Only)

**Endpoint**: `PUT /api/elections/{id}/close`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"
$electionId = "550e8400-e29b-41d4-a716-446655440000"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/close" `
    -Method PUT `
    -Headers $headers
```

---

## 👥 Candidate Management

### 8. Add Candidate to Election (Admin Only)

**Endpoint**: `POST /api/elections/{electionId}/candidates`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"
$electionId = "550e8400-e29b-41d4-a716-446655440000"

$body = @{
    name = "Alice Johnson"
    description = "Computer Science student, passionate about technology"
    metadata = "Department: CS, Year: 3"
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/candidates" `
    -Method POST `
    -Headers $headers `
    -Body $body
```

**Request Body**:
```json
{
    "name": "Alice Johnson",
    "description": "Computer Science student, passionate about technology",
    "metadata": "Department: CS, Year: 3"
}
```

**Response**:
```json
{
    "id": "660e8400-e29b-41d4-a716-446655440000",
    "name": "Alice Johnson",
    "description": "Computer Science student, passionate about technology",
    "metadata": "Department: CS, Year: 3",
    "electionId": "550e8400-e29b-41d4-a716-446655440000",
    "createdAt": "2024-12-13T01:00:00Z"
}
```

---

### 9. Get Candidates by Election

**Endpoint**: `GET /api/elections/{electionId}/candidates`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"
$electionId = "550e8400-e29b-41d4-a716-446655440000"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/candidates" `
    -Method GET `
    -Headers $headers
```

**Response**:
```json
[
    {
        "id": "660e8400-e29b-41d4-a716-446655440000",
        "name": "Alice Johnson",
        "description": "Computer Science student",
        "voteCount": 0
    },
    {
        "id": "770e8400-e29b-41d4-a716-446655440000",
        "name": "Bob Smith",
        "description": "Engineering student",
        "voteCount": 0
    }
]
```

---

## 🗳️ Voting System

### 10. Cast Vote

**Endpoint**: `POST /api/votes`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"

$body = @{
    electionId = "550e8400-e29b-41d4-a716-446655440000"
    candidateId = "660e8400-e29b-41d4-a716-446655440000"
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/votes" `
    -Method POST `
    -Headers $headers `
    -Body $body
```

**Request Body**:
```json
{
    "electionId": "550e8400-e29b-41d4-a716-446655440000",
    "candidateId": "660e8400-e29b-41d4-a716-446655440000"
}
```

**Response**:
```json
{
    "id": "880e8400-e29b-41d4-a716-446655440000",
    "electionId": "550e8400-e29b-41d4-a716-446655440000",
    "candidateId": "660e8400-e29b-41d4-a716-446655440000",
    "userId": "990e8400-e29b-41d4-a716-446655440000",
    "castAt": "2024-12-13T01:30:00Z",
    "ballotHash": "a1b2c3d4e5f6...",
    "isCounted": true
}
```

---

### 11. Get Election Results

**Endpoint**: `GET /api/elections/{electionId}/results`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"
$electionId = "550e8400-e29b-41d4-a716-446655440000"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/results" `
    -Method GET `
    -Headers $headers
```

**Response**:
```json
{
    "electionId": "550e8400-e29b-41d4-a716-446655440000",
    "electionTitle": "Student Council Election 2024",
    "totalVotes": 150,
    "candidates": [
        {
            "id": "660e8400-e29b-41d4-a716-446655440000",
            "name": "Alice Johnson",
            "voteCount": 85,
            "percentage": 56.67
        },
        {
            "id": "770e8400-e29b-41d4-a716-446655440000",
            "name": "Bob Smith",
            "voteCount": 65,
            "percentage": 43.33
        }
    ]
}
```

---

### 12. Get User's Vote History

**Endpoint**: `GET /api/votes/history`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/votes/history" `
    -Method GET `
    -Headers $headers
```

**Response**:
```json
[
    {
        "id": "880e8400-e29b-41d4-a716-446655440000",
        "electionTitle": "Student Council Election 2024",
        "candidateName": "Alice Johnson",
        "castAt": "2024-12-13T01:30:00Z",
        "ballotHash": "a1b2c3d4e5f6..."
    }
]
```

---

## 👤 User Management

### 13. Get All Users (Admin Only)

**Endpoint**: `GET /api/users`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/users" `
    -Method GET `
    -Headers $headers
```

**Response**:
```json
[
    {
        "id": "990e8400-e29b-41d4-a716-446655440000",
        "username": "john.doe@example.com",
        "email": "john.doe@example.com",
        "role": "USER",
        "createdAt": "2024-12-13T00:00:00Z"
    }
]
```

---

### 14. Get User Profile

**Endpoint**: `GET /api/users/profile`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"

$headers = @{
    "Authorization" = "Bearer $token"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/users/profile" `
    -Method GET `
    -Headers $headers
```

**Response**:
```json
{
    "userId": "990e8400-e29b-41d4-a716-446655440000",
    "fullName": "John Doe",
    "department": "Computer Science",
    "rollNo": "CS2021001",
    "photoUrl": "https://example.com/photo.jpg",
    "extra": "Additional info"
}
```

---

### 15. Update User Profile

**Endpoint**: `PUT /api/users/profile`

**PowerShell Command**:
```powershell
$token = "YOUR_JWT_TOKEN_HERE"

$body = @{
    fullName = "John Doe"
    department = "Computer Science"
    rollNo = "CS2021001"
    photoUrl = "https://example.com/photo.jpg"
    extra = "Year: 3, GPA: 3.8"
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/users/profile" `
    -Method PUT `
    -Headers $headers `
    -Body $body
```

**Request Body**:
```json
{
    "fullName": "John Doe",
    "department": "Computer Science",
    "rollNo": "CS2021001",
    "photoUrl": "https://example.com/photo.jpg",
    "extra": "Year: 3, GPA: 3.8"
}
```

---

## 🌐 Web UI Usage

### Access the Web Interface

1. **Open Browser**: Navigate to `http://localhost:8081`
2. **You'll see 12 sections** for different functionalities

### Using the Web UI

#### Section 1: User Registration
1. Enter email/username
2. Enter password
3. Click "Register"
4. Copy the JWT token from response

#### Section 2: User Login
1. Enter email/username
2. Enter password
3. Click "Login"
4. Copy the JWT token from response

#### Section 3: Create Election (Admin)
1. Paste your JWT token
2. Enter election title
3. Enter description
4. Set start date/time
5. Set end date/time
6. Click "Create Election"
7. Copy the election ID from response

#### Section 4: Get All Elections
1. Paste your JWT token
2. Click "Get Elections"
3. View all elections

#### Section 5: Add Candidate (Admin)
1. Paste your JWT token
2. Enter election ID
3. Enter candidate name
4. Enter description
5. Click "Add Candidate"

#### Section 6: Get Candidates
1. Paste your JWT token
2. Enter election ID
3. Click "Get Candidates"
4. View all candidates for that election

#### Section 7: Cast Vote
1. Paste your JWT token
2. Enter election ID
3. Enter candidate ID
4. Click "Cast Vote"

#### Section 8: Get Results
1. Paste your JWT token
2. Enter election ID
3. Click "Get Results"
4. View vote counts and percentages

#### Section 9: Vote History
1. Paste your JWT token
2. Click "Get History"
3. View your voting history

#### Section 10: Get All Users (Admin)
1. Paste your JWT token
2. Click "Get Users"
3. View all registered users

#### Section 11: Get Profile
1. Paste your JWT token
2. Click "Get Profile"
3. View your profile details

#### Section 12: Update Profile
1. Paste your JWT token
2. Fill in profile details
3. Click "Update Profile"

---

## 🧪 Complete Testing Workflow

### Step 1: Register Users

```powershell
# Register User 1
$user1 = @{
    usernameOrEmail = "alice@example.com"
    password = "Pass123"
} | ConvertTo-Json

$response1 = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" `
    -Method POST -ContentType "application/json" -Body $user1
$token1 = $response1.token

# Register User 2
$user2 = @{
    usernameOrEmail = "bob@example.com"
    password = "Pass123"
} | ConvertTo-Json

$response2 = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" `
    -Method POST -ContentType "application/json" -Body $user2
$token2 = $response2.token

Write-Host "User 1 Token: $token1"
Write-Host "User 2 Token: $token2"
```

### Step 2: Create Election (Need Admin Token)

```powershell
# You'll need to manually create an admin user in H2 console
# Or modify a user's role to ADMIN (role_id = 2)

$adminToken = "YOUR_ADMIN_TOKEN"

$election = @{
    title = "Test Election 2024"
    description = "Testing the voting system"
    startAt = "2024-12-13T00:00:00Z"
    endAt = "2024-12-20T23:59:59Z"
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $adminToken"
    "Content-Type" = "application/json"
}

$electionResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/elections" `
    -Method POST -Headers $headers -Body $election

$electionId = $electionResponse.id
Write-Host "Election ID: $electionId"
```

### Step 3: Add Candidates

```powershell
$candidate1 = @{
    name = "Candidate A"
    description = "First candidate"
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $adminToken"
    "Content-Type" = "application/json"
}

$cand1Response = Invoke-RestMethod `
    -Uri "http://localhost:8081/api/elections/$electionId/candidates" `
    -Method POST -Headers $headers -Body $candidate1

$candidateId1 = $cand1Response.id

# Add second candidate
$candidate2 = @{
    name = "Candidate B"
    description = "Second candidate"
} | ConvertTo-Json

$cand2Response = Invoke-RestMethod `
    -Uri "http://localhost:8081/api/elections/$electionId/candidates" `
    -Method POST -Headers $headers -Body $candidate2

$candidateId2 = $cand2Response.id

Write-Host "Candidate 1 ID: $candidateId1"
Write-Host "Candidate 2 ID: $candidateId2"
```

### Step 4: Publish Election

```powershell
$headers = @{
    "Authorization" = "Bearer $adminToken"
}

Invoke-RestMethod `
    -Uri "http://localhost:8081/api/elections/$electionId/publish" `
    -Method PUT -Headers $headers

Write-Host "Election published!"
```

### Step 5: Cast Votes

```powershell
# User 1 votes for Candidate A
$vote1 = @{
    electionId = $electionId
    candidateId = $candidateId1
} | ConvertTo-Json

$headers1 = @{
    "Authorization" = "Bearer $token1"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/votes" `
    -Method POST -Headers $headers1 -Body $vote1

Write-Host "User 1 voted for Candidate A"

# User 2 votes for Candidate B
$vote2 = @{
    electionId = $electionId
    candidateId = $candidateId2
} | ConvertTo-Json

$headers2 = @{
    "Authorization" = "Bearer $token2"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "http://localhost:8081/api/votes" `
    -Method POST -Headers $headers2 -Body $vote2

Write-Host "User 2 voted for Candidate B"
```

### Step 6: View Results

```powershell
$headers = @{
    "Authorization" = "Bearer $token1"
}

$results = Invoke-RestMethod `
    -Uri "http://localhost:8081/api/elections/$electionId/results" `
    -Method GET -Headers $headers

$results | ConvertTo-Json -Depth 10
```

---

## 📱 Testing with Postman

### Import Collection

1. **Create New Collection**: "VotingBank API"
2. **Set Base URL Variable**: `{{baseUrl}}` = `http://localhost:8081`
3. **Set Token Variable**: `{{token}}` = (your JWT token)

### Configure Requests

#### 1. Register User
- **Method**: POST
- **URL**: `{{baseUrl}}/api/auth/register`
- **Body** (JSON):
```json
{
    "usernameOrEmail": "test@example.com",
    "password": "Pass123"
}
```

#### 2. Login
- **Method**: POST
- **URL**: `{{baseUrl}}/api/auth/login`
- **Body** (JSON):
```json
{
    "usernameOrEmail": "test@example.com",
    "password": "Pass123"
}
```
- **Tests** (to save token):
```javascript
pm.environment.set("token", pm.response.json().token);
```

#### 3. Create Election
- **Method**: POST
- **URL**: `{{baseUrl}}/api/elections`
- **Headers**: `Authorization: Bearer {{token}}`
- **Body** (JSON):
```json
{
    "title": "Test Election",
    "description": "Description",
    "startAt": "2024-12-13T00:00:00Z",
    "endAt": "2024-12-20T23:59:59Z"
}
```

---

## 🔍 Common Issues & Solutions

### Issue 1: "Unauthorized" Error
**Solution**: Make sure you're including the JWT token in the Authorization header:
```
Authorization: Bearer YOUR_TOKEN_HERE
```

### Issue 2: "Election not found"
**Solution**: Verify the election ID is correct and the election exists.

### Issue 3: "User has already voted"
**Solution**: Each user can only vote once per election. Use a different user account.

### Issue 4: "Election is not published"
**Solution**: Admin must publish the election before users can vote.

### Issue 5: "Election has ended"
**Solution**: Check the election's end date. You cannot vote after it has ended.

---

## 📊 Database Queries (H2 Console)

Access H2 Console at: `http://localhost:8081/h2-console`

### View All Users
```sql
SELECT * FROM USERS;
```

### View All Elections
```sql
SELECT * FROM ELECTIONS;
```

### View All Candidates
```sql
SELECT * FROM CANDIDATES;
```

### View All Votes
```sql
SELECT * FROM VOTES;
```

### View Vote Counts
```sql
SELECT 
    c.name AS candidate_name,
    COUNT(v.id) AS vote_count
FROM CANDIDATES c
LEFT JOIN VOTES v ON c.id = v.candidate_id
WHERE c.election_id = 'YOUR_ELECTION_ID'
GROUP BY c.id, c.name
ORDER BY vote_count DESC;
```

### View User Roles
```sql
SELECT 
    u.username,
    u.email,
    r.name AS role
FROM USERS u
JOIN ROLES r ON u.role_id = r.id;
```

---

## 🎓 Best Practices

1. **Always save your JWT token** after login/registration
2. **Use environment variables** for tokens in production
3. **Check election status** before attempting to vote
4. **Verify candidate IDs** before casting votes
5. **Handle errors gracefully** in your client application
6. **Test with multiple users** to simulate real scenarios
7. **Monitor H2 console** to verify data persistence

---

## 📞 Support

For issues or questions:
- Check the logs in the terminal
- Verify database state in H2 console
- Review API responses for error messages
- Ensure all required fields are provided

---

**Happy Voting! 🗳️**
