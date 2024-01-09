-- Drop statements only during testing period
DROP TABLE IF EXISTS donations CASCADE;
DROP TABLE IF EXISTS registrations CASCADE;
DROP TABLE IF EXISTS blood_donors CASCADE;
DROP TABLE IF EXISTS secretariats CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS role_permissions CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;

CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE roles (
                       role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL
);

CREATE TABLE user_roles (
                            user_id BIGINT,
                            role_id BIGINT,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(user_id),
                            FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE permissions (
                             permission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(50) NOT NULL
);

CREATE TABLE role_permissions (
                                  role_id BIGINT,
                                  permission_id BIGINT,
                                  PRIMARY KEY (role_id, permission_id),
                                  FOREIGN KEY (role_id) REFERENCES roles(role_id),
                                  FOREIGN KEY (permission_id) REFERENCES permissions(permission_id)
);

CREATE TABLE blood_donors (
                              donor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              firstname VARCHAR(50) NOT NULL,
                              lastname VARCHAR(50) NOT NULL,
                              bloodtype INTEGER,
                              sex INTEGER,
                              birthdate DATE NOT NULL,
                              amka BIGINT UNIQUE,
                              region VARCHAR(50) NOT NULL,
                              phone VARCHAR(10) NOT NULL,
                              bloodwork VARCHAR(255) NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE secretariats (
                              secretariat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              name VARCHAR(50) NOT NULL,
                              surname VARCHAR(50) NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE donations (
                           donation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           donationDate DATE NOT NULL,
                           blood_donor_id BIGINT NOT NULL,
                           FOREIGN KEY (blood_donor_id) REFERENCES blood_donors(donor_id)
);

CREATE TABLE registrations (
                               registration_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               status INTEGER NOT NULL,
                               submission_date DATE NOT NULL,
                               blood_donor_id BIGINT NOT NULL,
                               secretariat_id BIGINT NOT NULL,
                               FOREIGN KEY (blood_donor_id) REFERENCES blood_donors(donor_id),
                               FOREIGN KEY (secretariat_id) REFERENCES secretariats(secretariat_id)
);
