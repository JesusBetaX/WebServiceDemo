DROP DATABASE IF EXISTS WebServiceDemoDB;
CREATE DATABASE WebServiceDemoDB;
USE WebServiceDemoDB;

CREATE TABLE persona (
    id INT NOT NULL auto_increment,
    nombre VARCHAR(50),
    apellidos VARCHAR(50),
    PRIMARY KEY(id)
);