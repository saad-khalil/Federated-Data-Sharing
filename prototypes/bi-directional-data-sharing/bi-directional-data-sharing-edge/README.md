# Bilateral Data Sharing Approach with Nuxt Instances

## Overview

This project implements a bilateral data sharing approach that allows users to log in with any credentials and connect different Nuxt instances. This README provides a guide on how to set up and use the bilateral data sharing feature with an example of two Nuxt instances running on localhost. Databases are present but empty.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)

## Prerequisites

Before you begin, ensure you have the following installed:

- [Node.js](https://nodejs.org/) 
- [npm](https://www.npmjs.com/) 

## Installation

1. Install dependencies:

    ```bash
    npm install
    ```

## Usage

1. Run Nuxt instances on localhost (npm run build for production):

    ```bash
    # First instance
    npm run dev 

    # Second instance 
    npm run dev 

    # nth instance ...
    npm run dev
    ```

2. Open your browser and navigate to [http://localhost:3000](http://localhost:3000) and [http://localhost:3001](http://localhost:3001) to ensure both instances are running. 

3. Login in with any credentials.

4. In the connection modal which appears after pressing **connection**, enter the following details:

    - **Address:** 127.0.0.1 (for localhost)
    - **Port:** Each instance is given a port, enter the respective ports in the respective nodes that should be connected to.

5. Connect to the instances and observe bilateral data sharing in action.
