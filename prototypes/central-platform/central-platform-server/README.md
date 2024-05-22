# Central platform server

This repository contains the server-side code for a Java Spring Boot application utilizing Java SDK 17.0.3 and PostgreSQL 16.

## Getting Started

To set up the server, follow these steps:

### Prerequisites

- Java SDK 17.0.3
- PostgreSQL 16

### Database Setup

1. Create a PostgreSQL database named `Hamza`.
2. Make sure PostgreSQL is running and accessible.
3. You may configure the database connection details in the `application.yml` file which should be placed in `src/main/resources`.

### Configuration

#### `application.yml`

The `application.yml` file in the `src/main/resources` directory is used for configuring the application settings. It should include database connection details, server configurations, and other relevant properties.

An example configuration file `example.application.yml` has been provided for reference.

### Running the Server

1. Open the project in your preferred Java IDE (Intelij is recommended).
2. Build and run the project to start the Spring Boot server.

## Project Structure

The project is structured as a typical Java Spring Boot application:

- `src/main/java`: Contains the Java source code.
- `src/main/resources`: Holds the resources, including the `application.yml` configuration file.

The java code is organized into the following folders:

- `config`: Contains configuration classes for the application.
- `controller`: Includes classes handling incoming HTTP requests and managing endpoints.
- `dto`: Holds Data Transfer Objects for data exchange.
- `exception`: Houses custom exception classes (currently empty).
- `model`: Contains the data model or entity classes for (JPA and Hibernate).
- `repository`: Includes classes for interacting with the database.
- `security`: Holds security-related classes such as configurations, filters, and authentication.
- `service`: Contains business logic and services.
- `util.Converters`: Holds converters used across the application.
- `payload`: Specifies request data formats for receiving and sending requests.