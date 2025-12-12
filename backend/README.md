# VotingBank Application - User Guide

## Overview
VotingBank is a secure voting system built with Spring Boot. The application provides a complete voting platform with user authentication, election management, candidate registration, and secure voting capabilities.

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/LuckyKrishnani/Voting_App.git
   cd Voting_App/backend
   ```

2. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application:**
   - Open your browser and navigate to: `http://localhost:8081`
   - The application runs on port 8081

## How to Use the Application

### Web Interface (Recommended for Testing)

The application includes a comprehensive testing UI at `http://localhost:8081` with 12 sections:

#### 1. **User Registration**
- Fill in username/email and password
- Click "Register" to create a new account
- You'll receive a token (for testing purposes)

#### 2. **User Login**
- Enter your username/email and password
- Click "Login" to authenticate
- Save the returned token for API calls

#### 3. **Create Election**
- Fill in election details:
  - Title (e.g., "Student Council Election 2024")
  - Description
  - Start Date & Time
  - End Date & Time
- Click "Create Election"
- Note the returned election ID

#### 4. **Get All Elections**
- Click "Get All Elections" to view all available elections
- Returns a list of all elections with their details

#### 5. **Get Election by ID**
- Enter an election ID
- Click "Get Election" to view specific election details

#### 6. **Create Candidate**
- Enter candidate details:
  - Name
  - Description
  - Election ID (from step 3)
- Click "Create Candidate"
- Note the returned candidate ID

#### 7. **Get All Candidates**
- Click "Get All Candidates" to view all registered candidates

#### 8. **Get Candidate by ID**
- Enter a candidate ID
- Click "Get Candidate" to view specific candidate details

#### 9. **Cast Vote**
- Enter:
  - Election ID
  - Candidate ID
  - User ID (your user ID from registration)
- Click "Cast Vote"
- Each user can only vote once per election

#### 10. **Get All Votes**
- Click "Get All Votes" to view all cast votes
- Shows encrypted ballot information

#### 11. **Get User Profile**
- Enter a user ID
- Click "Get Profile" to view user details

#### 12. **Update User Profile**
- Enter user ID and profile details:
  - Full Name
  - Roll Number
  - Department
- Click "Update Profile"

### API Endpoints (For Direct API Access)

#### Authentication
```bash
# Register a new user
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"user@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"user@example.com","password":"password123"}'
```

#### Elections
```bash
# Create election
curl -X POST http://localhost:8081/api/elections \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Student Election 2024",
    "description":"Annual student council election",
    "startAt":"2024-12-15T00:00:00Z",
    "endAt":"2024-12-20T00:00:00Z"
  }'

# Get all elections
curl http://localhost:8081/api/elections

# Get election by ID
curl http://localhost:8081/api/elections/{election-id}
```

#### Candidates
```bash
# Create candidate
curl -X POST http://localhost:8081/api/candidates \
  -H "Content-Type: application/json" \
  -d '{
    "name":"John Doe",
    "description":"Experienced leader",
    "electionId":"election-uuid-here"
  }'

# Get all candidates
curl http://localhost:8081/api/candidates

# Get candidate by ID
curl http://localhost:8081/api/candidates/{candidate-id}
```

#### Voting
```bash
# Cast vote
curl -X POST http://localhost:8081/api/votes \
  -H "Content-Type: application/json" \
  -d '{
    "electionId":"election-uuid-here",
    "candidateId":"candidate-uuid-here",
    "userId":"user-uuid-here"
  }'

# Get all votes
curl http://localhost:8081/api/votes
```

#### User Profile
```bash
# Get user profile
curl http://localhost:8081/api/users/{user-id}

# Update user profile
curl -X PUT http://localhost:8081/api/users/me/profile \
  -H "Content-Type: application/json" \
  -d '{
    "fullName":"John Doe",
    "rollNo":"2024001",
    "department":"Computer Science"
  }'
```

## Typical Workflow

1. **Register Users**: Create accounts for voters
2. **Create Election**: Set up an election with title, description, and dates
3. **Add Candidates**: Register candidates for the election
4. **Cast Votes**: Users vote for their preferred candidates
5. **View Results**: Check votes and election status

## Features

- ✅ **No Authentication Required**: All endpoints are accessible without JWT tokens (for testing)
- ✅ **Secure Voting**: Votes are encrypted and hashed
- ✅ **One Vote Per User**: Each user can only vote once per election
- ✅ **User Profiles**: Manage user information
- ✅ **Election Management**: Create and manage multiple elections
- ✅ **Candidate Registration**: Add multiple candidates per election
- ✅ **H2 In-Memory Database**: Data resets on application restart (perfect for testing)

## Database

The application uses an H2 in-memory database that:
- Automatically creates tables on startup
- Resets data when the application restarts
- Initializes with USER and ADMIN roles

## Configuration

Key settings in `application.properties`:
- **Port**: 8081
- **Database**: H2 in-memory (jdbc:h2:mem:votingbank)
- **DDL Auto**: create-drop (recreates schema on startup)
- **Security**: Disabled for testing

## Troubleshooting

### Application won't start
- Ensure port 8081 is not in use
- Check Java version (requires Java 17+)
- Verify Maven is installed

### Database errors
- The application uses create-drop, so tables are recreated on each startup
- If you see "Table not found" errors, restart the application

### Can't access endpoints
- Verify the application is running: check for "Started VotingBankApplication" in logs
- Ensure you're using the correct URL: http://localhost:8081
- Security is disabled, so no authentication is needed

## Support

For issues or questions:
- Check the application logs in the terminal
- Review the error messages in the web UI response sections
- Ensure all required fields are filled in forms

## GitHub Repository

https://github.com/LuckyKrishnani/Voting_App

## License

This project is for educational and testing purposes.
