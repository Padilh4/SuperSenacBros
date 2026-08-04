CREATE DATABASE TOPSCORES;
USE TOPSCORES;

CREATE TABLE Players(
name varchar(6) unique,
score int,
teachet varchar(4)
);

DROP TABLE Players;