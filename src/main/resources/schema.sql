-- -- Drop statements only during testing period
--DROP TABLE IF EXISTS role_permissions CASCADE;
--DROP TABLE IF EXISTS registrations CASCADE;
--DROP TABLE IF EXISTS donations CASCADE;
--DROP TABLE IF EXISTS blood_donors CASCADE;
--DROP TABLE IF EXISTS secreteriats CASCADE;
--DROP TABLE IF EXISTS users CASCADE;
--DROP TABLE IF EXISTS roles CASCADE;



CREATE TABLE IF NOT EXISTS roles (
                                     role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL
    );



-- User Table
CREATE TABLE IF NOT EXISTS users (
                                     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(15) NOT NULL,
    password VARCHAR(1500) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role_id BIGINT NOT NULL,
    is_account_non_expired BOOLEAN NOT NULL DEFAULT FALSE,
    is_account_non_locked BOOLEAN NOT NULL DEFAULT FALSE,
    is_credentials_non_expired BOOLEAN NOT NULL DEFAULT FALSE,
    is_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    password_last_changed_date DATE,
    failed_login_attempts INT DEFAULT 0,
    account_lock_date DATE,
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

-- Registrations Table
CREATE TABLE IF NOT EXISTS registrations (
                                             registration_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             status VARCHAR(20) NOT NULL,
    submission_date DATE NOT NULL,
    blood_donor_id BIGINT NOT NULL,
    --secretariat_id BIGINT,
    FOREIGN KEY (blood_donor_id) REFERENCES blood_donors(donor_id)
    --FOREIGN KEY (secretariat_id) REFERENCES secretariats(secretariat_id)
    );

INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');

INSERT INTO roles (name)
SELECT 'ROLE_BLOOD_DONOR'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_BLOOD_DONOR');

INSERT INTO roles (name)
SELECT 'ROLE_SECRETARIAT'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_SECRETARIAT');

--INSERT INTO users (username, password, email, role_id, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
--VALUES ('adminuser', 'password', 'admin@example.com', 1, TRUE, TRUE, TRUE, TRUE);
