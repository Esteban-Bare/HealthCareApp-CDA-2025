CREATE DATABASE IF NOT EXISTS diabete_scoring_db;
CREATE DATABASE IF NOT EXISTS note_db;

GRANT ALL PRIVILEGES ON diabete_scoring_db.* TO 'serviceuser'@'%';
GRANT ALL PRIVILEGES ON note_db.* TO 'serviceuser'@'%';
GRANT ALL PRIVILEGES ON patient_db.* TO 'serviceuser'@'%';

FLUSH PRIVILEGES;