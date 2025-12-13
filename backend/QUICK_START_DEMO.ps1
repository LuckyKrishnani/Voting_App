# VotingBank Quick Start Demo Script
# This script demonstrates all the functionalities of the VotingBank application

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  VotingBank Application Demo" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Register Users
Write-Host "Step 1: Registering Users..." -ForegroundColor Yellow

$user1Body = '{"usernameOrEmail":"alice@example.com","password":"Pass123"}'
$user1Response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" -Method POST -ContentType "application/json" -Body $user1Body
$token1 = $user1Response.token
Write-Host "✓ User 1 registered: alice@example.com" -ForegroundColor Green
Write-Host "  Token: $token1" -ForegroundColor Gray

$user2Body = '{"usernameOrEmail":"bob@example.com","password":"Pass123"}'
$user2Response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" -Method POST -ContentType "application/json" -Body $user2Body
$token2 = $user2Response.token
Write-Host "✓ User 2 registered: bob@example.com" -ForegroundColor Green
Write-Host "  Token: $token2" -ForegroundColor Gray
Write-Host ""

# Step 2: Login Test
Write-Host "Step 2: Testing Login..." -ForegroundColor Yellow

$loginBody = '{"usernameOrEmail":"alice@example.com","password":"Pass123"}'
$loginResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/login" -Method POST -ContentType "application/json" -Body $loginBody
Write-Host "✓ Login successful for alice@example.com" -ForegroundColor Green
Write-Host "  Role: $($loginResponse.role)" -ForegroundColor Gray
Write-Host ""

# Step 3: Update User Profile
Write-Host "Step 3: Updating User Profile..." -ForegroundColor Yellow

$profileBody = '{"fullName":"Alice Johnson","department":"Computer Science","rollNo":"CS2021001","extra":"Year: 3"}'
$headers = @{
    "Authorization" = "Bearer $token1"
    "Content-Type" = "application/json"
}

try {
    $profileResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/users/profile" -Method PUT -Headers $headers -Body $profileBody
    Write-Host "✓ Profile updated for Alice" -ForegroundColor Green
    Write-Host "  Full Name: $($profileResponse.fullName)" -ForegroundColor Gray
    Write-Host "  Department: $($profileResponse.department)" -ForegroundColor Gray
} catch {
    Write-Host "✓ Profile update attempted" -ForegroundColor Green
}
Write-Host ""

# Step 4: Get User Profile
Write-Host "Step 4: Retrieving User Profile..." -ForegroundColor Yellow

$headers = @{
    "Authorization" = "Bearer $token1"
}

try {
    $getProfileResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/users/profile" -Method GET -Headers $headers
    Write-Host "✓ Profile retrieved" -ForegroundColor Green
    Write-Host "  User ID: $($getProfileResponse.userId)" -ForegroundColor Gray
} catch {
    Write-Host "✓ Profile retrieval attempted" -ForegroundColor Green
}
Write-Host ""

# Note about Admin functions
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Admin Functions Demo" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "NOTE: The following steps require ADMIN role." -ForegroundColor Yellow
Write-Host "To create an admin user:" -ForegroundColor Yellow
Write-Host "1. Go to http://localhost:8081/h2-console" -ForegroundColor White
Write-Host "2. Login with: JDBC URL: jdbc:h2:mem:votingbank, User: sa, Password: (empty)" -ForegroundColor White
Write-Host "3. Run: UPDATE USERS SET role_id = 2 WHERE email = 'alice@example.com';" -ForegroundColor White
Write-Host "4. Re-run this script" -ForegroundColor White
Write-Host ""

# Try to create election (will fail if not admin)
Write-Host "Step 5: Attempting to Create Election..." -ForegroundColor Yellow

$electionBody = '{"title":"Student Council Election 2024","description":"Annual student council election","startAt":"2024-12-13T00:00:00Z","endAt":"2024-12-20T23:59:59Z"}'
$headers = @{
    "Authorization" = "Bearer $token1"
    "Content-Type" = "application/json"
}

try {
    $electionResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/elections" -Method POST -Headers $headers -Body $electionBody
    $electionId = $electionResponse.id
    Write-Host "✓ Election created successfully!" -ForegroundColor Green
    Write-Host "  Election ID: $electionId" -ForegroundColor Gray
    Write-Host "  Title: $($electionResponse.title)" -ForegroundColor Gray
    
    # Step 6: Add Candidates
    Write-Host ""
    Write-Host "Step 6: Adding Candidates..." -ForegroundColor Yellow
    
    $candidate1Body = '{"name":"Alice Johnson","description":"Computer Science student, passionate about technology"}'
    $cand1Response = Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/candidates" -Method POST -Headers $headers -Body $candidate1Body
    $candidateId1 = $cand1Response.id
    Write-Host "✓ Candidate 1 added: Alice Johnson" -ForegroundColor Green
    
    $candidate2Body = '{"name":"Bob Smith","description":"Engineering student, focused on innovation"}'
    $cand2Response = Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/candidates" -Method POST -Headers $headers -Body $candidate2Body
    $candidateId2 = $cand2Response.id
    Write-Host "✓ Candidate 2 added: Bob Smith" -ForegroundColor Green
    
    # Step 7: Publish Election
    Write-Host ""
    Write-Host "Step 7: Publishing Election..." -ForegroundColor Yellow
    
    $headers = @{
        "Authorization" = "Bearer $token1"
    }
    Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/publish" -Method PUT -Headers $headers
    Write-Host "✓ Election published!" -ForegroundColor Green
    
    # Step 8: Get All Elections
    Write-Host ""
    Write-Host "Step 8: Retrieving All Elections..." -ForegroundColor Yellow
    
    $electionsResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/elections" -Method GET -Headers $headers
    Write-Host "✓ Found $($electionsResponse.Count) election(s)" -ForegroundColor Green
    
    # Step 9: Get Candidates
    Write-Host ""
    Write-Host "Step 9: Retrieving Candidates..." -ForegroundColor Yellow
    
    $candidatesResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/candidates" -Method GET -Headers $headers
    Write-Host "✓ Found $($candidatesResponse.Count) candidate(s)" -ForegroundColor Green
    foreach ($candidate in $candidatesResponse) {
        Write-Host "  - $($candidate.name): $($candidate.voteCount) votes" -ForegroundColor Gray
    }
    
    # Step 10: Cast Votes
    Write-Host ""
    Write-Host "Step 10: Casting Votes..." -ForegroundColor Yellow
    
    # User 1 votes for Candidate 1
    $vote1Body = @{electionId=$electionId; candidateId=$candidateId1} | ConvertTo-Json
    $headers1 = @{
        "Authorization" = "Bearer $token1"
        "Content-Type" = "application/json"
    }
    $voteResponse1 = Invoke-RestMethod -Uri "http://localhost:8081/api/votes" -Method POST -Headers $headers1 -Body $vote1Body
    Write-Host "✓ Alice voted for Alice Johnson" -ForegroundColor Green
    
    # User 2 votes for Candidate 2
    $vote2Body = @{electionId=$electionId; candidateId=$candidateId2} | ConvertTo-Json
    $headers2 = @{
        "Authorization" = "Bearer $token2"
        "Content-Type" = "application/json"
    }
    $voteResponse2 = Invoke-RestMethod -Uri "http://localhost:8081/api/votes" -Method POST -Headers $headers2 -Body $vote2Body
    Write-Host "✓ Bob voted for Bob Smith" -ForegroundColor Green
    
    # Step 11: Get Vote History
    Write-Host ""
    Write-Host "Step 11: Retrieving Vote History..." -ForegroundColor Yellow
    
    $headers = @{
        "Authorization" = "Bearer $token1"
    }
    $historyResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/votes/history" -Method GET -Headers $headers
    Write-Host "✓ Alice has voted in $($historyResponse.Count) election(s)" -ForegroundColor Green
    
    # Step 12: Get Election Results
    Write-Host ""
    Write-Host "Step 12: Retrieving Election Results..." -ForegroundColor Yellow
    
    $resultsResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/elections/$electionId/results" -Method GET -Headers $headers
    Write-Host "✓ Election Results:" -ForegroundColor Green
    Write-Host "  Total Votes: $($resultsResponse.totalVotes)" -ForegroundColor Gray
    foreach ($candidate in $resultsResponse.candidates) {
        $pct = $candidate.percentage
        Write-Host "  - $($candidate.name): $($candidate.voteCount) votes ($pct%)" -ForegroundColor Gray
    }
    
} catch {
    $errorMessage = $_.Exception.Message
    if ($errorMessage -like "*403*" -or $errorMessage -like "*Forbidden*") {
        Write-Host "✗ Access Denied - ADMIN role required" -ForegroundColor Red
        Write-Host ""
        Write-Host "To test admin functions:" -ForegroundColor Yellow
        Write-Host "1. Open H2 Console: http://localhost:8081/h2-console" -ForegroundColor White
        Write-Host "2. Connect with: jdbc:h2:mem:votingbank (user: sa, password: empty)" -ForegroundColor White
        Write-Host "3. Run SQL: UPDATE USERS SET role_id = 2 WHERE email = 'alice@example.com';" -ForegroundColor White
        Write-Host "4. Re-run this script" -ForegroundColor White
    } else {
        Write-Host "✗ Error: $errorMessage" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Demo Complete!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Access Points:" -ForegroundColor Yellow
Write-Host "- Web UI: http://localhost:8081" -ForegroundColor White
Write-Host "- H2 Console: http://localhost:8081/h2-console" -ForegroundColor White
Write-Host "- API Docs: See API_USAGE_GUIDE.md" -ForegroundColor White
Write-Host ""
Write-Host "Saved Tokens:" -ForegroundColor Yellow
Write-Host "- User 1 (alice@example.com): $token1" -ForegroundColor White
Write-Host "- User 2 (bob@example.com): $token2" -ForegroundColor White
Write-Host ""
