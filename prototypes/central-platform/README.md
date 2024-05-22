# Centralized Platform Prototype

This repository houses the code for a comprehensive full-stack web application, combining a Nuxt.js frontend and a Spring Boot backend (refer to respective READMEs for further details).

The application, upon startup, generates multiple example organizations with admin users (e.g., email: admin@grolsch.com, password: adminPassword). These admin users have the capability to accept new users into their organizations and manage data access for other organizations.

## Data Access

- View which organizations' data an admin user's organization can access. (admin only)
- View external organizations that have data access with functionalities to add or remove access. (admin only)

## User Management

- Admin users can approve or decline user applications to join their organization. (admin only)

## Dashboard

- Displays accessible organizations and their location inventories, showing only inventory items related to the user or organization.
- Utilizes markers for each location with custom colors, representing the highest capacity inventory item visible at that location.
- Clicking on a marker presents a pop-up showing the highest capacity usage, general location information, and a link to the specific location page (currently empty).
