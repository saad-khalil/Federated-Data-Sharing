# Centralized Server Prototype 

This repository contains the frontend code for the Centralized Server Prototype, implemented using Nuxt.js.

## Project Structure

### Frontend

- `assets`: Contains Tailwind.
- `components`: Houses reusable Vue components used throughout the application. Includes a Navbar component with logout functionality.
- `composables`: Contains Vue Composition API logic for sharing across components (currently empty).
- `layouts`: Includes layout files for the application views (default and Authorization).
- `middleware`: Holds middleware files for route navigation guards (authentication).
- `pages`: Contains the application's views and routes.
- `plugins`: Includes Toast plugin.
- `public`: Holds static files.
- `store`: Houses Pinia store files for managing state.
- `types`: Includes TypeScript type definitions.
- `utils`: Consists of an Icon Factory for generating custom Nuxt Leaflet components, as it's an incomplete library not fully implemented by the official library.

## Getting Started

### Prerequisites

- Node.js

### Running the Project

1. Install dependencies with `npm install`.
2. Run the project in development mode using `npm run dev`.
3. Access the application by navigating to the specified local development server (usually http://localhost:3000 , using other routes might lead to CORS issues).