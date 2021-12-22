CREATE DATABASE IF NOT EXISTS books;

CREATE USER 'db_manager' IDENTIFIED BY 'db_manager';
GRANT ALL PRIVILEGES ON books.* TO 'db_manager';

CREATE USER 'web_user' IDENTIFIED BY 'web_user';
-- GRANT SELECT, INSERT, UPDATE, DELETE ON books.* TO 'web_user';
GRANT ALL PRIVILEGES ON books.* TO 'web_user';