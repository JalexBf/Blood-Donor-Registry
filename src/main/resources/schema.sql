-- Drop statements only during testing period
DROP TABLE IF EXISTS donations CASCADE;
DROP TABLE IF EXISTS registrations CASCADE;
DROP TABLE IF EXISTS blood_donors CASCADE;
DROP TABLE IF EXISTS secretariats CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- User Table
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       is_account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
                       is_account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
                       is_credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
                       is_enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Role Column added in User table
ALTER TABLE users ADD COLUMN role VARCHAR(50) NOT NULL;

-- Blood Donors Table
CREATE TABLE blood_donors (
                              donor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              firstname VARCHAR(50) NOT NULL,
                              lastname VARCHAR(50) NOT NULL,
                              bloodtype VARCHAR(20) NOT NULL,
                              sex VARCHAR(20) NOT NULL,
                              birthdate DATE NOT NULL,
                              amka BIGINT UNIQUE,
                              region VARCHAR(50) NOT NULL,
                              phone VARCHAR(10) NOT NULL,
                              bloodwork VARCHAR(255),
                              FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Secretariats Table
CREATE TABLE secretariats (
                              secretariat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              firstname VARCHAR(50) NOT NULL,
                              lastname VARCHAR(50) NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Donations Table
CREATE TABLE donations (
                           donation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           donationDate DATE NOT NULL,
                           blood_donor_id BIGINT NOT NULL,
                           FOREIGN KEY (blood_donor_id) REFERENCES blood_donors(donor_id)
);

-- Registrations Table
CREATE TABLE registrations (
                               registration_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               status VARCHAR(20) NOT NULL,
                               submission_date DATE NOT NULL,
                               blood_donor_id BIGINT NOT NULL,
                               secretariat_id BIGINT NOT NULL,
                               FOREIGN KEY (blood_donor_id) REFERENCES blood_donors(donor_id),
                               FOREIGN KEY (secretariat_id) REFERENCES secretariats(secretariat_id)
);


INSERT INTO users (username, email, password, role) VALUES ('jason', 'it218142@hua.gr', '123456789', 'ADMIN');
