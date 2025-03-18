# System Interfaces and Operations

This project implements the system interfaces and operations for Sprint 2 of the application.

## Project Structure

```
├── src/
│   ├── config/         # Configuration files
│   ├── controllers/    # API route controllers
│   ├── interfaces/     # Interface definitions
│   ├── models/         # Database models
│   ├── operations/     # Interface implementations
│   ├── utils/          # Utility functions
│   └── index.js        # Main application entry point
├── .env                # Environment variables
├── package.json        # Project dependencies
└── README.md           # Project documentation
```

## Key Features

- **Authentication System**: User registration, login, and token management
- **User Management**: CRUD operations for user accounts with role-based access control
- **Content Management**: Create, read, update, and delete content with publishing workflow

## Prerequisites

- Node.js (v14 or higher)
- MongoDB (local or cloud instance)

## Installation

1. Clone the repository
2. Install dependencies:
   ```
   npm install
   ```
3. Configure environment variables in `.env`

## Running the Application

```
# Development mode with auto-reload
npm run dev

# Production mode
npm start

# Run tests
npm test
```

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login a user
- `GET /api/auth/me` - Get current user
- `POST /api/auth/refresh` - Refresh token
- `POST /api/auth/logout` - Logout user

### Users

- `GET /api/users` - Get all users (admin only)
- `GET /api/users/:id` - Get user by ID
- `POST /api/users` - Create a new user (admin only)
- `PUT /api/users/:id` - Update user
- `DELETE /api/users/:id` - Delete user (admin only)
- `POST /api/users/change-password` - Change password

### Content

- `GET /api/content` - Get all content
- `GET /api/content/:id` - Get content by ID
- `POST /api/content` - Create new content
- `PUT /api/content/:id` - Update content
- `DELETE /api/content/:id` - Delete content
- `PUT /api/content/:id/publish` - Publish content
- `PUT /api/content/:id/unpublish` - Unpublish content 