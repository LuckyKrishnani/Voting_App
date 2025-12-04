# Voting Bank - Project Structure

## Architecture Overview

The Voting Bank project follows a modern client-server architecture with clear separation of concerns:

```
voting_bank/
├── backend/                    # Java Spring Boot API Server
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/votingbank/
│   │   │   │       ├── config/              # Configuration classes
│   │   │   │       ├── controller/          # REST API endpoints
│   │   │   │       ├── service/             # Business logic
│   │   │   │       ├── repository/          # Database access
│   │   │   │       ├── entity/              # JPA entities
│   │   │   │       ├── dto/                 # Data transfer objects
│   │   │   │       ├── security/            # JWT & security
│   │   │   │       ├── exception/           # Custom exceptions
│   │   │   │       └── VotingBankApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── application-prod.properties
│   │   └── test/
│   ├── pom.xml                # Maven dependencies
│   └── README.md
│
├── frontend/                   # Flutter Mobile Application
│   ├── lib/
│   │   ├── main.dart
│   │   ├── config/             # App configuration
│   │   ├── constants/          # App constants
│   │   ├── data/
│   │   │   ├── datasources/    # API calls
│   │   │   ├── models/         # Data models
│   │   │   └── repositories/   # Repository pattern
│   │   ├── presentation/
│   │   │   ├── bloc/           # BLoC state management
│   │   │   ├── pages/          # Full-page screens
│   │   │   ├── widgets/        # Reusable components
│   │   │   └── routes/         # Navigation
│   │   └── domain/
│   │       ├── entities/       # Business entities
│   │       └── usecases/       # Business logic
│   ├── pubspec.yaml            # Flutter dependencies
│   ├── test/                   # Unit & widget tests
│   └── README.md
│
├── database/                   # PostgreSQL Schema & Migrations
│   ├── migrations/
│   ├── seeds/
│   └── schema.sql
│
├── docs/                       # Documentation
│   ├── API_DOCUMENTATION.md
│   ├── ARCHITECTURE.md
│   ├── DEPLOYMENT.md
│   └── TESTING.md
│
└── README.md                   # Project root documentation
```

## Technology Stack

| Component | Technology | Purpose |
|-----------|-----------|---------|
| **Frontend** | Flutter (Dart) | Cross-platform mobile application |
| **State Management** | BLoC (Business Logic Component) | Reactive state management |
| **Backend** | Java Spring Boot | RESTful API server |
| **Database** | PostgreSQL | Persistent data storage |
| **Authentication** | JWT (JSON Web Token) | Secure user sessions |
| **Communication** | HTTPS/REST | Encrypted API communication |
| **Testing** | JUnit, Mockito (Backend), Flutter Test (Frontend) | Quality assurance |

## Key Features

### For Voters
- Secure login/registration
- View ongoing elections
- Cast single vote per election
- View final results after poll closes
- Vote history tracking

### For Administrators
- Create and manage elections
- Add/remove candidates
- View live vote counts
- Close elections and publish results
- User management

### Security Features
- JWT-based authentication
- One-vote-per-user validation
- HTTPS/TLS encryption
- Secure password hashing (bcrypt)
- Input validation and sanitization
- SQL injection prevention
- CORS configuration
- Rate limiting on auth endpoints

## Development Workflow

1. **Backend Development**: Implement Spring Boot APIs with PostgreSQL
2. **Frontend Development**: Build Flutter UI with BLoC state management
3. **Integration Testing**: Test API-Frontend communication
4. **Security Testing**: Validate authentication and authorization
5. **Documentation**: Generate API docs and deployment guides
6. **Deployment**: Package and deploy to production

## Database Design

The system uses PostgreSQL with the following main tables:

- **users**: User accounts with roles (voter/admin)
- **elections**: Election/poll definitions
- **candidates**: Candidates in each election
- **votes**: Cast votes with tamper-proof validation
- **vote_history**: Audit trail for voting activity

## API Endpoints

All endpoints are prefixed with `/api/v1/`

### Authentication
- `POST /auth/register` - User registration
- `POST /auth/login` - User login
- `POST /auth/refresh` - Refresh JWT token
- `POST /auth/logout` - User logout

### Elections (Admin)
- `POST /elections` - Create election
- `PUT /elections/{id}` - Update election
- `DELETE /elections/{id}` - Delete election
- `GET /elections` - List all elections
- `GET /elections/{id}` - Get election details
- `POST /elections/{id}/close` - Close election

### Candidates (Admin)
- `POST /elections/{electionId}/candidates` - Add candidate
- `DELETE /elections/{electionId}/candidates/{candidateId}` - Remove candidate
- `GET /elections/{electionId}/candidates` - List candidates

### Voting (Voter)
- `GET /elections/active` - Get active elections
- `POST /elections/{electionId}/vote` - Cast vote
- `GET /elections/{electionId}/results` - Get results
- `GET /user/votes` - Get user's voting history

## Environment Configuration

### Backend (application.properties)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/voting_bank
spring.datasource.username=voting_user
spring.datasource.password=secure_password
spring.jpa.hibernate.ddl-auto=validate
jwt.secret=your-secret-key
jwt.expiration=86400000
server.port=8080
```

### Frontend (Flutter)
```dart
const String API_BASE_URL = 'https://api.votingbank.com';
const String API_TIMEOUT = 30; // seconds
```

## Running the Project

### Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
flutter pub get
flutter run
```

### Database Setup
```bash
psql -U postgres -f database/schema.sql
```
