
+# Contact Registry Web Application
+
+A Java-based web application for managing a contact registry. It allows users to add, view, update, and delete contact information. The application also features an admin dashboard for contact statistics and a reporting page with filtering capabilities.
+
+## Features
+
+-   **CRUD Operations:** Create, Read, Update, and Delete contacts.
+-   **Contact Listing:** View all contacts on the main page (`index.jsp`).
+-   **Admin Dashboard (`admin.jsp`):**
+    -   View statistics on contacts by gender.
+    -   View statistics on contacts by county of residence.
+    -   See a list of the 5 most recently added contacts.
+-   **Reporting (`report.jsp`):**
+    -   View a comprehensive list of all contacts.
+    -   Filter contacts by county of residence.
+
+## Technologies Used
+
+-   **Backend:** Java, Jakarta Servlets
+-   **Frontend:** JSP (JavaServer Pages), HTML (CSS and JavaScript would typically be used for styling and client-side interactions)
+-   **Database:** MySQL
+-   **Servlet Container:** Apache Tomcat (or any other Jakarta EE compatible servlet container like Jetty, WildFly, etc.)
+-   **JDBC Driver:** MySQL Connector/J
+
+## Setup and Installation
+
+### Prerequisites
+
+-   Java Development Kit (JDK) 8 or newer.
+-   Apache Tomcat 9 or 10 (or a similar servlet container).
+-   MySQL Server (version 5.7 or newer recommended).
+-   An IDE like IntelliJ IDEA, Eclipse, or VS Code (optional, for development).
+-   Apache Maven or Gradle (optional, if you plan to manage dependencies and build with these tools).
+
+### Database Setup
+
+1.  **Start MySQL Server.**
+2.  **Create the database:**
+    ```sql
+    CREATE DATABASE contact_registry;
+    ```
+3.  **Use the database:**
+    ```sql
+    USE contact_registry;
+    ```
+4.  **Create the `contacts` table:**
+    ```sql
+    CREATE TABLE contacts (
+        id BIGINT AUTO_INCREMENT PRIMARY KEY,
+        full_name VARCHAR(255) NOT NULL,
+        phone_number VARCHAR(20),
+        email_address VARCHAR(255),
+        id_number VARCHAR(50) UNIQUE,
+        date_of_birth DATE,
+        gender VARCHAR(10),
+        county_of_residence VARCHAR(100)
+    );
+    ```
+
+### Application Configuration
+
+1.  **Database Connection:**
+    The database connection details are configured in `src/main/java/com/cyrus/DatabaseConnection.java`.
+    ```java
+    package com.cyrus;
+
+    public class DatabaseConnection {
+        public static final String URL = "jdbc:mysql://localhost:3306/contact_registry";
+        public static final String USER = "root";
+        public static final String PASSWORD = ""; // <-- IMPORTANT: Update with your MySQL root password if set
+        public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
+    }
+    ```
+    Ensure the `URL`, `USER`, and `PASSWORD` constants match your MySQL setup.
+
+### Build & Deployment
+
+1.  **Compile Java Files:** Compile all `.java` files located in `src/main/java/`. If using an IDE, this is often handled automatically.
2.  **Package the Application:**
+    Create a Web Application Archive (WAR) file. The structure should be:
+    ```
+    contact-registry-web-app.war
+    ├── WEB-INF/
+    │   ├── classes/  (contains all compiled .class files, e.g., com/cyrus/ContactServlet.class)
+    │   └── lib/      (contains necessary JARs, e.g., mysql-connector-j.jar, jakarta.servlet-api.jar if not provided by server)
+    ├── index.jsp
+    ├── admin.jsp
+    ├── report.jsp
+    └── (other JSPs, HTML, CSS, JS files)
+    ```
+    *Note: If you are using Maven or Gradle, these tools will handle the packaging process for you (`mvn package` or `gradle build`). You would need to add a `pom.xml` or `build.gradle` file to your project.*
+3.  **Deploy to Servlet Container:**
+    Copy the `contact-registry-web-app.war` (or your chosen WAR file name) to the `webapps` directory of your Apache Tomcat installation. Tomcat will automatically deploy it.
+
+### Accessing the Application
+
+Once deployed, the application can be accessed via the following URLs (assuming Tomcat is running on `localhost:8080` and the application context path is `contact-registry-web-app`):
+
+-   **Main Page (View/Add Contacts):** `http://localhost:8080/contact-registry-web-app/contact`
+    -   Initially, this page might be intended to be `index.jsp` which then calls `/contact` to load data. The `ContactServlet` forwards to `index.jsp`.
+-   **Admin Dashboard:** `http://localhost:8080/contact-registry-web-app/admin`
+-   **Reports Page:** `http://localhost:8080/contact-registry-web-app/ReportServlet`
+
+*The context path might vary depending on your WAR file name or Tomcat configuration.*
+
+## Application Endpoints
+
+The application exposes the following servlet endpoints:
+
+-   **`com.cyrus.ContactServlet` (mapped to `/contact`)**
+    -   `GET`: Fetches all contacts and displays them on `index.jsp`.
+    -   `POST`: Adds a new contact to the database. Expects form parameters: `full_name`, `phone_number`, `email`, `id_number`, `date_of_birth`, `gender`, `county_of_residence`. Redirects to `index.jsp`.
+    -   `PUT`: Updates an existing contact. Expects parameters: `id`, `phone_number`, `email`, `id_number`, `date_of_birth`, `full_name`, `gender`, `county_of_residence`.
+    -   `DELETE`: Deletes a contact. Expects parameter: `id`.
+
+-   **`com.cyrus.AdminDashboardServlet` (mapped to `/admin`)**
+    -   `GET`: Fetches contact statistics (gender, county) and recent contacts, then forwards to `admin.jsp`.
+
+-   **`com.cyrus.ReportServlet` (mapped to `/report`)**
+    -   `GET`: Fetches contacts (optionally filtered by `county` request parameter) and a list of distinct counties, then forwards to `report.jsp`.
+
+## Usage
+
+1.  Navigate to `http://localhost:8080/contact-registry-web-app/contact` (or `index.jsp` if you have one that serves as the entry point) to view existing contacts.
+2.  Use the forms on the page (presumably on `index.jsp` or a dedicated "add contact" page) to submit new contact details.
+3.  To update or delete contacts, you would typically have buttons/links next to each contact in the list that trigger `PUT` or `DELETE` requests to `/contact` with the appropriate contact `id`.
+4.  Access `/admin` to view the dashboard.
+5.  Access `/ReportServlet` to view reports and use the filter options.
+
+---
+
+This README provides a good starting point. You can expand it further with details about specific JSPs, form field validations, or any other unique aspects of your project.

