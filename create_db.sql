--
-- Script de Creación de la base de datos.
--
DROP DATABASE IF EXISTS EjemploLogin;
CREATE DATABASE IF NOT EXISTS EjemploLogin;
USE EjemploLogin;

--
-- Tabla Usuario
--
DROP TABLE IF EXISTS Usuario;
CREATE TABLE Usuario (
     id_usuario SERIAL PRIMARY KEY,
     nombre_usuario VARCHAR(30) NOT NULL,
     password VARCHAR(200),
     bloqueado BOOLEAN DEFAULT FALSE,
    nombre VARCHAR(200),
    apellido VARCHAR(200),
    fecha_de_nacimiento DATE,
    UNIQUE(nombre_usuario)
);

INSERT INTO Usuario(nombre_usuario, password, nombre, apellido) VALUES('john.doe', 'DeepCafe', 'John', 'Doe');

--
-- Vista Usuario_Login
--
DROP VIEW IF EXISTS Usuario_Login;
CREATE VIEW Usuario_Login AS
    SELECT id_usuario, nombre_usuario, password, bloqueado
      FROM Usuario;

--
-- Tabla Sesion
--
DROP TABLE IF EXISTS Sesion;
CREATE TABLE Sesion (
    id_sesion SERIAL PRIMARY KEY,
    id_usuario BIGINT UNSIGNED NOT NULL,
    inicio_sesion DATETIME DEFAULT NOW(),
    fin_sesion DATETIME DEFAULT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario)
);

--
-- Creamos un stored procedure para finalizar una sesión.
--
DROP PROCEDURE IF EXISTS finalizarSesion;

DELIMITER //

CREATE PROCEDURE finalizarSesion(IN id_sesion_in BIGINT UNSIGNED)
BEGIN
    UPDATE EjemploLogin.Sesion SET fin_sesion = NOW() WHERE id_sesion = id_sesion_in;
END //

DELIMITER ;

--
-- Creamos un usuario 'login'@'%' y le asignamos permisos
-- para operar con la vista Usuario_Login y la tabla Sesion
--
DROP USER 'login'@'%';
CREATE USER 'login'@'%' IDENTIFIED BY 'LoginApp';
GRANT USAGE ON EjemploLogin.* TO 'login'@'%';

GRANT SELECT ON EjemploLogin.Usuario_Login TO 'login'@'%';
GRANT SELECT, INSERT, UPDATE ON EjemploLogin.Sesion TO 'login'@'%';
GRANT EXECUTE ON PROCEDURE EjemploLogin.finalizarSesion TO 'login'@'%';

--
-- Fin Script de creación
--