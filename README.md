# File Processing Application

## Overview

The File Processing Application is a Java-based project designed to read and process data from input files (CSV and JSON formats) and write the processed data to output files. It utilizes Spring Boot for the web interface and OpenClover for code coverage, demonstrating best practices in software development.

## Requirements

- Java 21
- Maven 3.6 or later

## Setup

1. **Clone the Repository** (if applicable):
    ```bash
    git clone https://github.com/yourusername/fileprocessing.git
    cd fileprocessing
    ```

2. **Build the Project**:
    ```bash
    mvn clean install
    ```

3. **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```

   Access the application by navigating to `http://localhost:8080` in your web browser.

## Usage

- **Uploading Files**:
    - Navigate to `http://localhost:8080`.
    - Use the interface to select and upload a CSV or JSON file.
    - The application processes the file and provides a link to download the output.

- **Viewing Reports**:
    - The Clover report can be generated and viewed by following the steps in the Testing section below.

## Testing

- **Running Unit Tests**:
  Run unit tests using the following Maven command:
    ```bash
    mvn test
    ```

- **Generating Test Coverage Reports**:
  Generate the OpenClover test coverage report with:
    ```bash
    mvn clean clover:setup test clover:aggregate clover:clover
    ```
  You can find the report in `target/site/clover/index.html`. Open it with a web browser to view detailed code coverage statistics.
