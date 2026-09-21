# TaskFlow API

A Spring Boot task management backend API with CRUD operations and AI-powered task summarization.

## Prerequisites

- Java 21+
- Maven 3.6+
- PostgreSQL (running on localhost:5432)
- (Optional) REST client like Postman or VS Code REST Client extension

## Database Setup

### 1. Start PostgreSQL Service

```bash
sudo service postgresql start
```

**Verify PostgreSQL is running:**
```bash
sudo service postgresql status
```

**Expected output:**
```
● postgresql.service - PostgreSQL RDBMS
     Loaded: loaded (/usr/lib/systemd/system/postgresql.service; enabled; preset: enabled)
     Active: active (exited) since Mon 2026-09-21 19:21:06 +08; 3h 21min ago
 Invocation: 88cc0a9eebf245ddb638924be9ffd93f
    Process: 361 ExecStart=/bin/true (code=exited, status=0/SUCCESS)
   Main PID: 361 (code=exited, status=0/SUCCESS)
```

**Note:** `active (exited)` is normal — PostgreSQL runs as a background daemon after starting.

### 2. Create the Database

Connect to PostgreSQL and create the database:

```bash
$ sudo -u postgres psql
psql (18.6 (Ubuntu 18.6-0ubuntu0.26.04.1))
Type "help" for help.

postgres=#
```

Once inside the PostgreSQL prompt, create the database:

```bash
postgres=# CREATE DATABASE taskflowdb;
CREATE DATABASE
```

### 3. Verify the Database

Check that the database was created:

List all databases using `\l` command:

```bash
postgres=# \l
                                                  List of databases
    Name    |  Owner   | Encoding | Locale Provider | Collate |  Ctype  | Locale | ICU Rules |   Access privileges
------------+----------+----------+-----------------+---------+---------+--------+-----------+-----------------------
 postgres   | postgres | UTF8     | libc            | C.UTF-8 | C.UTF-8 |        |           |
 taskflowdb | postgres | UTF8     | libc            | C.UTF-8 | C.UTF-8 |        |           |
  ...
postgres=#
```

Exit the psql prompt using the `\q` command to return to shell.

You could also list all databases this way:

```bash
sudo -u postgres psql -l
```

You should see `taskflowdb` listed in the databases.

Alternatively, connect directly to verify:

```bash
sudo -u postgres psql -d taskflowdb
```

## Environment Configuration

### Set Up OpenAI API Key

The application uses Spring AI for task summarization. You need to configure your OpenAI API key.

#### Option 1: Using Environment Variable (Recommended)

1. **Add to your shell profile** (`~/.bashrc`, `~/.zshrc`, or `~/.profile`):

```bash
export OPENAI_API_KEY=your_actual_api_key_here
```

2. **Reload the shell**:

```bash
source ~/.bashrc
# or
source ~/.zshrc
```

3. **Verify it's set**:

```bash
echo $OPENAI_API_KEY
```

You should see your API key printed.

#### Option 2: Set Temporarily (For Current Session Only)

```bash
export OPENAI_API_KEY=your_actual_api_key_here
mvn clean spring-boot:run
```

**Note:** This only works for the current terminal session.

#### Option 3: Using .env File

Create a `.env` file in the project root:

```
OPENAI_API_KEY=your_actual_api_key_here
```

Then load it before running:

```bash
set -a
source .env
set +a
mvn clean spring-boot:run
```

**Security Tip:** Never commit the actual API key to Git. Add `.env` to `.gitignore` if using that approach.

## Running the Application

### 1. Start the application
```bash
mvn clean spring-boot:run
```

The server will start on `http://localhost:8088`

### 2. Verify it's running
You should see logs indicating:
- Spring Boot started successfully
- PostgreSQL connection established
- Tomcat server initialized on port 8088

## Testing the API

### Option 1: Using VS Code REST Client
If you have the REST Client extension installed, open `http/tasks.http` and click "Send Request" above each test.

### Option 2: Using cURL
Use the commands below to test each endpoint.

---

## API Endpoints

### 1. Create Tasks

**Request:**
```http
POST http://localhost:8088/api/tasks
Content-Type: application/json

{
  "title": "Complete Module 3",
  "description": "Finish assignment",
  "priority": "HIGH",
  "dueDate": "2026-05-20",
  "completed": false
}
```

**Expected Response (201 Created):**
```json
{
  "id": 1,
  "title": "Complete Module 3",
  "description": "Finish assignment",
  "priority": "HIGH",
  "dueDate": "2026-05-20",
  "completed": false
}
```

**cURL:**
```bash
curl -X POST http://localhost:8088/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Complete Module 3",
    "description": "Finish assignment",
    "priority": "HIGH",
    "dueDate": "2026-05-20",
    "completed": false
  }'
```

---

### 2. Get All Tasks

**Request:**
```http
GET http://localhost:8088/api/tasks
```

**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Complete Module 3",
    "description": "Finish assignment",
    "priority": "HIGH",
    "dueDate": "2026-05-20",
    "completed": false
  },
  {
    "id": 2,
    "title": "Walk Dog",
    "description": "Take the dog for a walk in the park",
    "priority": "MEDIUM",
    "dueDate": "2026-05-21",
    "completed": false
  }
]
```

**cURL:**
```bash
curl http://localhost:8088/api/tasks
```

---

### 3. Get Task by ID

**Request:**
```http
GET http://localhost:8088/api/tasks/1
```

**Expected Response (200 OK):**
```json
{
  "id": 1,
  "title": "Complete Module 3",
  "description": "Finish assignment",
  "priority": "HIGH",
  "dueDate": "2026-05-20",
  "completed": false
}
```

**Error Case - Task Not Found (404):**
```http
GET http://localhost:8088/api/tasks/9999
```

**Expected Response:**
```
Could not find task with id: 9999
```

**cURL:**
```bash
curl http://localhost:8088/api/tasks/1
```

---

### 4. Update Task

**Request:**
```http
PUT http://localhost:8088/api/tasks/1
Content-Type: application/json

{
  "title": "Complete Module 3 - Updated",
  "description": "Finish assignment with updates",
  "priority": "MEDIUM",
  "dueDate": "2026-05-25",
  "completed": false
}
```

**Expected Response (200 OK):**
```json
{
  "id": 1,
  "title": "Complete Module 3 - Updated",
  "description": "Finish assignment with updates",
  "priority": "MEDIUM",
  "dueDate": "2026-05-25",
  "completed": false
}
```

**cURL:**
```bash
curl -X PUT http://localhost:8088/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Complete Module 3 - Updated",
    "description": "Finish assignment with updates",
    "priority": "MEDIUM",
    "dueDate": "2026-05-25",
    "completed": false
  }'
```

---

### 5. Mark Task as Complete

**Request:**
```http
PUT http://localhost:8088/api/tasks/2/complete
```

**Expected Response (200 OK):**
```json
{
  "id": 2,
  "title": "Walk Dog",
  "description": "Take the dog for a walk in the park",
  "priority": "MEDIUM",
  "dueDate": "2026-05-21",
  "completed": true
}
```

**cURL:**
```bash
curl -X PUT http://localhost:8088/api/tasks/2/complete
```

---

### 6. Delete Task

**Request:**
```http
DELETE http://localhost:8088/api/tasks/1
```

**Expected Response (204 No Content):**
```
(empty response)
```

**cURL:**
```bash
curl -X DELETE http://localhost:8088/api/tasks/1
```

---

### 7. Get Task Summary (AI-Powered)

Generates a plain-English summary of pending and completed tasks using Spring AI.

**Request:**
```http
GET http://localhost:8088/api/tasks/summary
```

**Expected Response (200 OK):**
```
Of the tasks listed:

*   **Pending:** You have two tasks to do. You need to schedule a doctor's appointment (high priority) and finish the updated assignment for Module 3 (medium priority).
*   **Completed:** The task of walking the dog is already done.
```

**cURL:**
```bash
curl http://localhost:8088/api/tasks/summary
```

---

## Test Workflow

Follow this sequence to test all endpoints:

1. **Create 3 tasks** using the Create endpoint
2. **Get all tasks** and verify they appear
3. **Get a specific task** by its ID
4. **Update a task** with new values
5. **Mark a task as complete** using the complete endpoint
6. **Get task summary** to see AI-generated summary
7. **Delete a task** and verify it's gone

## Architecture

- **Model:** Plain POJO `Task` with Lombok
- **Repository:** Spring Data JPA with PostgreSQL
- **Service:** Business logic with constructor injection
- **Controller:** REST endpoints with proper HTTP status codes
- **Extras:** Spring AI integration for task summarization

## HTTP Status Codes

- `201 Created` — Task successfully created
- `200 OK` — Successful GET, PUT, or operation
- `204 No Content` — Task successfully deleted
- `404 Not Found` — Task ID does not exist
- `500 Internal Server Error` — Server error

## Notes

- All fields except `title` are optional when creating a task
- `completed` defaults to `false` if not provided
- Update endpoint requires all fields to be provided
- Delete returns no content (204) on success
- Summary endpoint uses Spring AI (requires API key configuration)
