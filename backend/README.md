# VotingBank - Secure Voting System Backend

A Spring Boot application for managing secure elections and voting with JWT authentication.

## Features

- **User Authentication & Authorization**: JWT-based authentication with role-based access control
- **Election Management**: Create and manage elections with start/end dates
- **Candidate Management**: Add candidates to elections with media support
- **Secure Voting**: Encrypted ballot system with vote verification
- **User Profiles**: Manage user information and profiles
- **File Attachments**: Support for candidate media and user profile photos

## Technology Stack

- **Framework**: Spring Boot 3.2.5
- **Language**: Java 17
- **Database**: H2 (in-memory for development)
- **Security**: Spring Security with JWT
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/LuckyKrishnani/Voting_App.git
cd Voting_App/backend
```

2. Configure application properties:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

3. Update the `application.properties` file with your configuration:
   - Set a secure JWT secret key
   - Configure database credentials (if using external DB)
   - Update admin credentials

4. Build the project:
```bash
mvn clean install
```

5. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Elections
- `GET /api/elections` - Get all elections
- `GET /api/elections/{id}` - Get election by ID
- `POST /api/elections` - Create new election (Admin only)
- `PUT /api/elections/{id}` - Update election (Admin only)
- `DELETE /api/elections/{id}` - Delete election (Admin only)

### Candidates
- `GET /api/candidates` - Get all candidates
- `GET /api/candidates/{id}` - Get candidate by ID
- `POST /api/candidates` - Create new candidate (Admin only)
- `PUT /api/candidates/{id}` - Update candidate (Admin only)
- `DELETE /api/candidates/{id}` - Delete candidate (Admin only)

### Voting
- `POST /api/votes` - Cast a vote
- `GET /api/votes/election/{electionId}` - Get votes for an election
- `GET /api/votes/user/{userId}` - Get user's votes

### Users
- `GET /api/users/me` - Get current user profile
- `PUT /api/users/me` - Update current user profile
- `GET /api/users` - Get all users (Admin only)

## Security

The application uses JWT (JSON Web Tokens) for authentication. To access protected endpoints:

1. Register or login to get a JWT token
2. Include the token in the Authorization header:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## Database Schema

The application uses the following main entities:
- **Users**: User accounts with authentication
- **Roles**: User roles (ADMIN, USER)
- **Elections**: Voting elections
- **Candidates**: Election candidates
- **Votes**: Cast votes with encryption
- **UserProfiles**: Extended user information
- **Attachments**: File attachments for various entities

## Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package -DskipTests
java -jar target/votingbank-0.0.1-SNAPSHOT.jar
```

## Configuration

Key configuration properties in `application.properties`:

- `server.port`: Application port (default: 8081)
- `jwt.secret`: Secret key for JWT token generation
- `jwt.expiration`: Token expiration time in milliseconds
- `spring.datasource.*`: Database configuration
- `spring.jpa.*`: JPA/Hibernate settings

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.

## Support

For issues and questions, please open an issue on GitHub.
