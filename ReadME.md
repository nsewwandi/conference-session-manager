# Conference Session Manager

This is a backend application designed to manage conference sessions, speakers, and related information. Built with **Dropwizard**, it integrates with a **MySQL** database to store session data and exposes REST APIs for creating, updating, retrieving, and deleting conference sessions. The application also includes features for managing session details such as title, description, and speaker information.

## Features

- **Session Management**: Create, update, view, and delete conference sessions.
- **Database Integration**: Connects to MySQL for session data storage.
- **API Security**: Implements basic authentication for secure access.
- **Flyway Migration**: Automates database migrations.
- **Docker Support**: Easily package and run the application using Docker.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Docker**: To run the application in containers.
- **Java 17**: Required for running the Dropwizard application.
- **Maven**: Required for building the project.
- **MySql**: MySQL is required. Create a database named "conference_db".

## Setting Up the Application

### Clone the Repository

```bash
git clone git@github.com:nsewwandi/conference-session-manager.git
cd conference-session-manager
```
### Build the application

```bash
mvn clean package
```

### Run the application

Using Java

```bash
java -jar target/ConferenceSessionManager-1.0-SNAPSHOT.jar server config.yml
```

Using Docker

```bash
docker build -t conference-session-manager .
docker run -p 8080:8080 -v "$PWD/config.yml:/config.yml" conference-session-manager
```

### Verify the Application

Once the application is running, you can test it using Postman or cURL:

```bash
curl -X GET http://localhost:8080/sessions
```
