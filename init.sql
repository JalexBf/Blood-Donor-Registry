CREATE TABLE IF NOT EXISTS roles (
                                     role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL
    );



INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');

INSERT INTO roles (name)
SELECT 'CITIZEN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'CITIZEN');

INSERT INTO roles (name)
SELECT 'ROLE_BLOOD_DONOR'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_BLOOD_DONOR');

INSERT INTO roles (name)
SELECT 'ROLE_SECRETARIAT'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_SECRETARIAT');


-- User Table
CREATE TABLE IF NOT EXISTS users (
                                     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(15) NOT NULL,
    password VARCHAR(1500) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    is_account_non_expired BOOLEAN NOT NULL DEFAULT FALSE,
    is_account_non_locked BOOLEAN NOT NULL DEFAULT FALSE,
    is_credentials_non_expired BOOLEAN NOT NULL DEFAULT FALSE,
    is_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    password_last_changed_date DATE,
    failed_login_attempts INT DEFAULT 0,
    account_lock_date DATE
    );



    CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
    );



-- Blood Donors Table
CREATE TABLE IF NOT EXISTS blood_donors (
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
    bloodwork_file_path VARCHAR(255),
    last_donation_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

-- Secretariats Table
CREATE TABLE IF NOT EXISTS secretariats (
                                            secretariat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            user_id BIGINT NOT NULL,
                                            firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

-- -- Donations Table
CREATE TABLE IF NOT EXISTS donations (
                                         donation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         donationDate DATE NOT NULL,
                                         blood_donor_id BIGINT NOT NULL,
                                         FOREIGN KEY (blood_donor_id) REFERENCES blood_donors(donor_id)
    );

-- applications Table
CREATE TABLE IF NOT EXISTS applications (
                                             application_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             status VARCHAR(20) NOT NULL,
    submission_date DATE NOT NULL,
    blood_donor_id BIGINT NOT NULL,
    FOREIGN KEY (blood_donor_id) REFERENCES blood_donors(donor_id)
    );

