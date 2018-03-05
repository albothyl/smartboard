CREATE DATABASE smartboard DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

CREATE USER 'smartuser'@'localhost' IDENTIFIED BY 'smartuser';

USE mysql;

INSERT INTO db (HOST,Db,USER,Select_priv,Insert_priv,Update_priv,Delete_priv,Create_priv,Drop_priv,Index_priv, Alter_priv)
VALUES('localhost','smartboard','smartuser','Y','Y','Y','Y','Y','Y','Y','Y');

FLUSH PRIVILEGES;