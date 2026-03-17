create database if not exists jpamysql;
CREATE USER if not exists 'spjain'@'%' IDENTIFIED BY 'spjain';
grant all privileges on jpamysql.* to 'spjain'@'%';
