CREATE USER 'db_manager' IDENTIFIED BY 'db_manager';
GRANT ALL PRIVILEGES ON Authentication.* TO 'db_manager';

CREATE USER 'web_user' IDENTIFIED BY 'web_user';
GRANT SELECT, INSERT, UPDATE, DELETE ON Authentication.* TO 'web_user';
