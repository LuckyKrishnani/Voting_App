# VotingBank Application - User Guide

## Overview
VotingBank is a secure voting system built with Spring Boot. The application provides a complete voting platform with user authentication, election management, candidate registration, and secure voting capabilities.

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git
- PostgreSQL (optional - for production use)
- pgAdmin (optional - for database management)

### Running the Application

#### Option 1: Development Mode (H2 Database - Default)

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
   - Web UI: http://localhost:8081
   - H2 Console: http://localhost:8081/h2-console
     - JDBC URL: `jdbc:h2:mem:votingbank`
     - Username: `sa`
     - Password: (leave empty)

**Note**: H2 is an in-memory database. All data is lost when you stop the application.

#### Option 2: Production Mode (PostgreSQL Database)

1. **Set up PostgreSQL**:
   - Follow the detailed guide in [DATABASE_SETUP.md](DATABASE_SETUP.md)
   - Create database `votingbank_db` using pgAdmin
   - Copy `application-prod.properties.example` to `application-prod.properties`
   - Update your PostgreSQL password in `application-prod.properties`

2. **Switch to production profile**:
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.profiles.active=prod
   ```

3. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
   - Web UI: http://localhost:8081
   - View database using pgAdmin

**Note**: PostgreSQL persists data across application restarts.

#### Quick Profile Switch

You can also switch profiles via command line without editing files:

```bash
# Development (H2)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production (PostgreSQL)
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

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

## Database Configuration

### Development Profile (H2 - Default)
- **Type**: In-memory database
- **URL**: `jdbc:h2:mem:votingbank`
- **Console**: http://localhost:8081/h2-console
- **Data Persistence**: No (resets on restart)
- **Use Case**: Testing and development
- **Setup**: No configuration needed

### Production Profile (PostgreSQL)
- **Type**: Persistent database
- **URL**: `jdbc:postgresql://localhost:5432/votingbank_db`
- **Management**: pgAdmin
- **Data Persistence**: Yes (survives restarts)
- **Use Case**: Production deployment
- **Setup**: See [DATABASE_SETUP.md](DATABASE_SETUP.md)

Both profiles automatically:
- Create tables on startup
- Initialize USER and ADMIN roles
- Set up all necessary relationships

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
