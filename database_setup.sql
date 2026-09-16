CREATE DATABASE IF NOT EXISTS detective_os;
CREATE USER IF NOT EXISTS 'detective'@'localhost' IDENTIFIED BY 'detective123';
ALTER USER 'detective'@'localhost' IDENTIFIED BY 'detective123';
GRANT ALL PRIVILEGES ON detective_os.* TO 'detective'@'localhost';
FLUSH PRIVILEGES;

USE detective_os;
-- Hibernate/JPA creates and updates the application tables automatically.
