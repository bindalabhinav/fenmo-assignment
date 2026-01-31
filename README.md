# Expense Tracker – Fenmo Technical Assignment

## Overview
This is a minimal full-stack Expense Tracker application that allows users to
record and review personal expenses. The system is designed with correctness,
simplicity, and production-readiness in mind, while keeping the feature set
intentionally small as per the assignment guidelines.

## Features
- Create a new expense with amount, category, description, and date
- View a list of all expenses
- Filter expenses by category
- Sort expenses by date (newest first)
- View total amount for the currently visible expenses
- Safe handling of retries and duplicate submissions

## Tech Stack
**Backend**
- Java 17
- Spring Boot
- Spring Data JPA
- H2 (file-based)

**Frontend**
- HTML
- Vanilla JavaScript

**Deployment**
- Docker
- Render

## API Endpoints
- `POST /expenses` – Create a new expense (idempotent)
- `GET /expenses` – List expenses with optional filtering and sorting

## Key Design Decisions

### Idempotent Expense Creation
To handle real-world conditions such as network retries, page refreshes, or
multiple submit clicks, the API requires a `requestId` for expense creation.
If the same request is retried, the backend returns the already-created expense
instead of creating duplicates.

### Money Handling
All monetary values are handled using `BigDecimal` to avoid floating-point
precision issues.

### Persistence Choice
The application uses file-based H2 for simplicity and fast setup. This allows
data persistence across local restarts. In a production environment, this would
be replaced with a managed relational database such as PostgreSQL.

### Frontend Simplicity
The frontend is intentionally kept simple (plain HTML + JavaScript) to focus on
correctness, clarity, and backend behavior rather than UI complexity.

## Trade-offs & Limitations
- No authentication or multi-user support
- No permanent database persistence across redeployments
- Minimal UI styling

These were intentional trade-offs to prioritize core correctness and
production-like behavior within the time constraint.

## How to Run Locally

```bash
cd expense-tracker
./mvnw spring-boot:run
```

## Then Open:
```md
http://localhost:8080
```

## Live Demo 
🔗 Live URL:https://fenmo-assignment.onrender.com/
