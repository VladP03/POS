CREATE DATABASE IF NOT EXISTS calculator;

CREATE USER 'vlad' IDENTIFIED BY 'tiger';
GRANT ALL PRIVILEGES ON calculator.* TO 'vlad';