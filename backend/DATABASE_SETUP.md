# PostgreSQL Database Setup Guide

This guide will help you set up PostgreSQL for the VotingBank application.

## Prerequisites

- PostgreSQL installed on your system
- pgAdmin (already installed)

## Step 1: Create Database Using pgAdmin

1. **Open pgAdmin**
   - Launch pgAdmin from your applications

2. **Connect to PostgreSQL Server**
   - Expand "Servers" in the left panel
   - Click on "PostgreSQL" (you may need to enter your password)

3. **Create New Database**
   - Right-click on "Databases"
   - Select "Create" → "Database..."
   - Enter the following details:
     - **Database Name**: `votingbank_db`
     - **Owner**: `postgres` (or your username)
   - Click "Save"

## Step 2: Configure Application

1. **Open the file**: `src/main/resources/application-prod.properties`

2. **Update the PostgreSQL password**:
   ```properties
   spring.datasource.password=your_actual_password
   ```
   Replace `your_actual_password` with your PostgreSQL password

3. **Verify other settings** (usually these are correct by default):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/votingbank_db
   spring.datasource.username=postgres
   ```

## Step 3: Switch to PostgreSQL Profile

**Option A: Edit application.properties**
1. Open `src/main/resources/application.properties`
2. Change the active profile:
   ```properties
   spring.profiles.active=prod
   ```

**Option B: Use Command Line**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Step 4: Run the Application

```bash
cd backend
mvn spring-boot:run
```

The application will:
- Connect to PostgreSQL
- Create all necessary tables automatically
- Initialize USER and ADMIN roles
- Start on port 8081

## Verify Database Setup

### Using pgAdmin:

1. In pgAdmin, expand:
   - Servers → PostgreSQL → Databases → votingbank_db → Schemas → public → Tables

2. You should see these tables:
   - `users`
   - `roles`
   - `user_profiles`
   - `elections`
   - `candidates`
   - `votes`
   - `attachments`
   - `candidate_media`

### Using SQL Query:

1. Right-click on `votingbank_db` → "Query Tool"
2. Run this query:
   ```sql
   SELECT table_name 
   FROM information_schema.tables 
   WHERE table_schema = 'public';
   ```

## Switching Between Databases

### Development (H2 - In-Memory):
```properties
spring.profiles.active=dev
```
- Data resets on restart
- No setup required
- H2 Console: http://localhost:8081/h2-console

### Production (PostgreSQL):
```properties
spring.profiles.active=prod
```
- Data persists across restarts
- Requires PostgreSQL setup
- View data using pgAdmin

## Common PostgreSQL Commands

### Check if PostgreSQL is Running:
```bash
# Windows
pg_ctl status

# Or check services
services.msc
# Look for "postgresql-x64-XX"
```

### Default PostgreSQL Settings:
- **Host**: localhost
- **Port**: 5432
- **Default User**: postgres
- **Default Database**: postgres

## Troubleshooting

### Error: "password authentication failed"
- Check your password in `application-prod.properties`
- Verify password in pgAdmin connection

### Error: "database does not exist"
- Create the database using pgAdmin (Step 1)
- Verify database name is `votingbank_db`

### Error: "Connection refused"
- Ensure PostgreSQL service is running
- Check port 5432 is not blocked by firewall

### Error: "role does not exist"
- Verify username in `application-prod.properties`
- Default username is usually `postgres`

## Database Schema

The application automatically creates these tables:

```
users
├── id (UUID, Primary Key)
├── username (Unique)
├── email (Unique)
├── password_hash
├── role_id (Foreign Key → roles)
├── created_at
└── last_login

roles
├── id (SmallInt, Primary Key)
└── name (Unique)

elections
├── id (UUID, Primary Key)
├── title
├── description
├── start_at
├── end_at
├── created_by (Foreign Key → users)
├── created_at
├── is_published
└── is_closed

candidates
├── id (UUID, Primary Key)
├── name
├── description
├── election_id (Foreign Key → elections)
├── metadata
└── created_at

votes
├── id (UUID, Primary Key)
├── election_id (Foreign Key → elections)
├── candidate_id (Foreign Key → candidates)
├── user_id (Foreign Key → users)
├── encrypted_ballot
├── ballot_hash
├── cast_at
└── is_counted
└── UNIQUE(election_id, user_id) -- One vote per user per election

user_profiles
├── user_id (UUID, Primary Key, Foreign Key → users)
├── full_name
├── roll_no
├── department
├── photo_url
├── extra
└── updated_at

attachments
├── id (UUID, Primary Key)
├── owner_type
├── owner_id
├── file_url
├── mime_type
├── uploaded_by (Foreign Key → users)
└── uploaded_at

candidate_media
├── id (UUID, Primary Key)
├── candidate_id (Foreign Key → candidates)
├── file_url
├── file_type
├── uploaded_by (Foreign Key → users)
└── uploaded_at
```

## Next Steps

After setting up PostgreSQL:
1. Access the application: http://localhost:8081
2. Use the web UI to test all features
3. View data in pgAdmin
4. Data will persist across application restarts

## Support

If you encounter any issues:
1. Check the application logs in the terminal
2. Verify PostgreSQL is running
3. Confirm database credentials are correct
4. Ensure the database `votingbank_db` exists
