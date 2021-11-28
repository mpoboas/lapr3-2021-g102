CREATE TABLE Person (
    idPerson INTEGER PRIMARY KEY,
    name VARCHAR(100),
    birthDate DATE
);

CREATE TABLE Occupation (
    idOccupation INTEGER PRIMARY KEY,
    designation VARCHAR(255),
    salary NUMBER(10,2)
);

CREATE TABLE Employee (
    idEmployee INTEGER PRIMARY KEY,
    idPerson INTEGER,
    idOccupation INTEGER
);
ALTER TABLE Employee ADD CONSTRAINT fk_Person1 FOREIGN KEY (idPerson) REFERENCES Person(idPerson);
ALTER TABLE Employee ADD CONSTRAINT fk_Occupation1 FOREIGN KEY (idOccupation) REFERENCES Occupation(idOccupation);

CREATE TABLE Vehicle (
    idVehicle INTEGER PRIMARY KEY
);

CREATE TABLE VesselType (
    idVesselType INTEGER PRIMARY KEY,
    designation VARCHAR(100)
);

CREATE TABLE Ship (
    idVehicle INTEGER PRIMARY KEY,
    MMSI INTEGER UNIQUE,
    IMO INTEGER UNIQUE,
    shipName VARCHAR(100),
    callSign VARCHAR(15),
    vesselType INTEGER,
    length NUMBER(10,2),
    width NUMBER(10,2),
    capacity INTEGER,
    draft NUMBER(10,2)
);
ALTER TABLE Ship ADD CONSTRAINT fk_VesselType FOREIGN KEY (vesselType) REFERENCES VesselType(idVesselType);
ALTER TABLE Ship ADD CONSTRAINT fk_Vehicle1 FOREIGN KEY (idVehicle) REFERENCES Vehicle(idVehicle);

CREATE TABLE Truck (
    idVehicle INTEGER PRIMARY KEY,
    idEmployee INTEGER
);
ALTER TABLE Truck ADD CONSTRAINT fk_Vehicle2 FOREIGN KEY (idVehicle) REFERENCES Vehicle(idVehicle);
ALTER TABLE Truck ADD CONSTRAINT fk_Employee1 FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee);

CREATE TABLE Container (
    idContainer INTEGER PRIMARY KEY,
    tare NUMBER(10,2),
    payload NUMBER(10,2),
    gross NUMBER(10,2),
    isoCode INTEGER,
    refrigerated INTEGER,
    temperature NUMBER(6,2)
);

CREATE TABLE Certificate (
    idCertificate INTEGER PRIMARY KEY,
    certificateName VARCHAR(100)
);

CREATE TABLE ContainerCertificate (
    idContainer INTEGER,
    idCertificate INTEGER
);
ALTER TABLE ContainerCertificate ADD CONSTRAINT pk_ContainerCertificate PRIMARY KEY (idContainer, idCertificate);
ALTER TABLE ContainerCertificate ADD CONSTRAINT fk_Container1 FOREIGN KEY (idContainer) REFERENCES Container(idContainer);
ALTER TABLE ContainerCertificate ADD CONSTRAINT fk_Certificate FOREIGN KEY (idCertificate) REFERENCES Certificate(idCertificate);

CREATE TABLE Position (
    idPosition INTEGER PRIMARY KEY,
    latitude BINARY_DOUBLE,
    longitude BINARY_DOUBLE
);

CREATE TABLE ShipPosition(
    baseDateTime TIMESTAMP PRIMARY KEY,
    sog NUMBER(10,2),
    cog NUMBER(10,2),
    heading INTEGER,
    transceiverClass CHAR,
    idShip INTEGER,
    idPosition INTEGER
);
ALTER TABLE ShipPosition ADD CONSTRAINT fk_Ship2 FOREIGN KEY (idShip) REFERENCES Ship(idVehicle);
ALTER TABLE ShipPosition ADD CONSTRAINT fk_Position1 FOREIGN KEY (idPosition) REFERENCES Position(idPosition);

CREATE TABLE PortWarehouse (
    idPortWarehouse INTEGER PRIMARY KEY,
    name VARCHAR(100),
    continent VARCHAR(100),
    country VARCHAR(100),
    idPosition INTEGER
);
ALTER TABLE PortWarehouse ADD CONSTRAINT fk_Position2 FOREIGN KEY (idPosition) REFERENCES Position(idPosition);

CREATE TABLE PortWarehouseStaff (
    idPortWarehouse INTEGER,
    idEmployee INTEGER
);
ALTER TABLE PortWarehouseStaff ADD CONSTRAINT pk_PortWarehouseStaff PRIMARY KEY (idPortWarehouse, idEmployee);
ALTER TABLE PortWarehouseStaff ADD CONSTRAINT fk_PortWarehouse1 FOREIGN KEY (idPortWarehouse) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE PortWarehouseStaff ADD CONSTRAINT fk_Employee2 FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee);

CREATE TABLE Port (
    idPortWarehouse INTEGER
);
ALTER TABLE Port ADD CONSTRAINT pk_Port PRIMARY KEY (idPortWarehouse);
ALTER TABLE Port ADD CONSTRAINT fk_PortWarehouse2 FOREIGN KEY (idPortWarehouse) REFERENCES PortWarehouse(idPortWarehouse);

CREATE TABLE Warehouse (
    idPortWarehouse INTEGER
);
ALTER TABLE Warehouse ADD CONSTRAINT pk_Warehouse PRIMARY KEY (idPortWarehouse);
ALTER TABLE Warehouse ADD CONSTRAINT fk_PortWarehouse3 FOREIGN KEY (idPortWarehouse) REFERENCES PortWarehouse(idPortWarehouse);

CREATE TABLE Trip (
    idTrip INTEGER PRIMARY KEY,
    idPortWarehouseDeparture INTEGER,
    idPortWarehouseArrival INTEGER,
    idVehicle INTEGER
);
ALTER TABLE Trip ADD CONSTRAINT fk_PortWarehouse4 FOREIGN KEY (idPortWarehouseDeparture) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE Trip ADD CONSTRAINT fk_PortWarehouse5 FOREIGN KEY (idPortWarehouseArrival) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE Trip ADD CONSTRAINT fk_idVehicle3 FOREIGN KEY (idVehicle) REFERENCES Vehicle(idVehicle);

CREATE TABLE Crew (
    idTrip INTEGER,
    idEmployee INTEGER
);
ALTER TABLE Crew ADD CONSTRAINT pk_ShipEmployee PRIMARY KEY (idTrip, idEmployee);
ALTER TABLE Crew ADD CONSTRAINT fk_Trip1 FOREIGN KEY (idTrip) REFERENCES Trip(idTrip);
ALTER TABLE Crew ADD CONSTRAINT fk_Employee3 FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee);

CREATE TABLE ArrivalDeparture (
    idTrip INTEGER,
    idPortWarehouse INTEGER,
    arrivalDepartureDate TIMESTAMP
);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT pk_ArrivalDeparture PRIMARY KEY (idTrip, idPortWarehouse);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT fk_Trip2 FOREIGN KEY (idTrip) REFERENCES Trip(idTrip);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT fk_PortWarehouse6 FOREIGN KEY (idPortWarehouse) REFERENCES PortWarehouse(idPortWarehouse);

CREATE TABLE Summary (
    idSummary INTEGER PRIMARY KEY,
    idTrip INTEGER,
    idPortWarehouse INTEGER,
    startDateTime TIMESTAMP,
    endDateTime TIMESTAMP,
    totalMovTime BINARY_DOUBLE,
    totalMovNumber INTEGER,
    maxSog NUMBER(10,2),
    meanCog NUMBER(10,2),
    startLat BINARY_DOUBLE,
    endLat BINARY_DOUBLE,
    startLong BINARY_DOUBLE,
    endLong BINARY_DOUBLE,
    travelledDistance BINARY_DOUBLE,
    deltaDistance BINARY_DOUBLE
);
ALTER TABLE Summary ADD CONSTRAINT fk_ArrivalDeparture1 FOREIGN KEY (idTrip, idPortWarehouse) REFERENCES ArrivalDeparture(idTrip, idPortWarehouse);

CREATE TABLE CargoManifestType (
    idCargoManifestType INTEGER PRIMARY KEY,
    designation VARCHAR(100)
);

CREATE TABLE CargoManifest (
    idCargoManifest INTEGER PRIMARY KEY,
    content VARCHAR(255),
    elaborationDate TIMESTAMP,
    idCargoManifestType INTEGER,
    idTrip INTEGER,
    idPortWarehouse INTEGER
);
ALTER TABLE CargoManifest ADD CONSTRAINT fk_CargoManifestType FOREIGN KEY (idCargoManifestType) REFERENCES CargoManifestType(idCargoManifestType);
ALTER TABLE CargoManifest ADD CONSTRAINT fk_ArrivalDeparture2 FOREIGN KEY (idTrip, idPortWarehouse) REFERENCES ArrivalDeparture(idTrip, idPortWarehouse);

CREATE TABLE ManifestContainer (
    idCargoManifest INTEGER,
    idContainer INTEGER,
    containerPositionX INTEGER,
    containerPositionY INTEGER,
    containerPositionZ INTEGER
);
ALTER TABLE ManifestContainer ADD CONSTRAINT pk_ManifestContainer PRIMARY KEY (idCargoManifest, idContainer);
ALTER TABLE ManifestContainer ADD CONSTRAINT fk_CargoManifest FOREIGN KEY (idCargoManifest) REFERENCES CargoManifest(idCargoManifest);
ALTER TABLE ManifestContainer ADD CONSTRAINT fk_Container2 FOREIGN KEY (idContainer) REFERENCES Container(idContainer);

CREATE TABLE UserRole (
    idUserRole INTEGER PRIMARY KEY,
    designation VARCHAR(100)
);

CREATE TABLE SystemUser (
    idUser INTEGER PRIMARY KEY,
    idEmployee INTEGER,
    idUserRole INTEGER
);
ALTER TABLE SystemUser ADD CONSTRAINT fk_Employee4 FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee);
ALTER TABLE SystemUser ADD CONSTRAINT fk_UserRole FOREIGN KEY (idUserRole) REFERENCES UserRole(idUserRole);