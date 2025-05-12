# Contact Registry Web App

## 📌 Project Overview
The **Contact Registry Web App** allows users to store, update, manage, and generate reports on contact details. It supports **CRUD operations (Create, Read, Update, Delete)** via a simple web interface. The project includes an **admin dashboard** that displays statistics on contacts by **gender** and **county**.

## 🚀 Features
- **Add, Edit, Delete, and View Contacts**
- **Filter contacts by county**
- **Print & Generate reports**
- **Admin dashboard with gender & county statistics**
- **REST API support for external integrations**

## 🛠️ Technologies Used
- **Java Servlets & JSP**
- **Maven** (Dependency Management)
- **MySQL** (Database)
- **JDBC** (Database Connectivity)
- **JavaScript & jQuery** (Frontend)
- **Apache Tomcat** (Server)

## 📂 Folder Structure
ContactRegistry
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── contactregistry
│   │   └── webapp
│   │       ├── WEB-INF
│   │       │   └── web.xml
│   │       ├── css
│   │       ├── js
│   │       └── index.jsp
└── pom.xml

## 🏗️ Project Structure

### **1️⃣ src/main/java/com/example/contactregistry**
- Contains Java classes for the application logic.

### **2️⃣ src/main/webapp**
- Contains JSP files, CSS, and JavaScript for the frontend.

### **3️⃣ pom.xml**
- Maven configuration file for dependency management.

## 🔧 Installation & Setup
### **1️⃣ Prerequisites**
- Install **JDK 17+**
- Install **Apache Tomcat**
- Install **MySQL**
- Install **Maven**

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/cyruskkorir/contact-registry-web-app.git
cd ContactRegistry