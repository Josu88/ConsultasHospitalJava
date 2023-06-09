CREATE DATABASE hospital;

USE hospital;


CREATE TABLE Hospitales (
 Codigo VARCHAR(5) PRIMARY KEY,
 Nombre VARCHAR(9) NOT NULL
);

 
 CREATE TABLE Gfh (
 CodiHospi VARCHAR(5) NOT NULL,
 Codigo VARCHAR(5) NOT NULL,
 Nombre VARCHAR(80) NOT NULL,
 primary key(Codigo,CodiHospi),
 FOREIGN KEY (CodiHospi) REFERENCES Hospitales(Codigo)
 ON DELETE cascade
 ON UPDATE cascade);


CREATE TABLE EQUIPOS (
	idEquipo INT AUTO_INCREMENT,
	CodiHospi VARCHAR(5) NOT NULL,
	Codigo VARCHAR(12) NOT NULL,
	Nombre VARCHAR(150) NOT NULL,
	Marca VARCHAR(50) NOT NULL,
	Modelo VARCHAR(50) NOT NULL,
	Serie VARCHAR(25) NOT NULL,
	PrecioCompra FLOAT NOT NULL,
	FechaHoraAlta datetime NOT NULL,
	CodigoGfh VARCHAR(10) NOT NULL,
	PRIMARY KEY (idEquipo),
	FOREIGN KEY (CodiHospi) REFERENCES Hospitales(Codigo)
    ON DELETE cascade
	ON UPDATE cascade,
	FOREIGN KEY (CodigoGfh) REFERENCES Gfh(Codigo)
    ON DELETE cascade
	ON UPDATE cascade);







