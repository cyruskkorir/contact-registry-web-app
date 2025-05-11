CREATE DATABASE ContactRegistry;
USE ContactRegistry;

CREATE TABLE Contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    gender ENUM('Male', 'Female', 'Other'),
    county VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);