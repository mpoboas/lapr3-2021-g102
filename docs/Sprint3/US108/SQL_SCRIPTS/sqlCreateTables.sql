CREATE TABLE Person (
    idPerson INTEGER PRIMARY KEY,
    name VARCHAR(100),
    birthDate DATE
);

CREATE TABLE SystemRole (
    idUserRole INTEGER PRIMARY KEY,
    designation VARCHAR(100)
);

CREATE TABLE SystemUser (
    idUser INTEGER PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE
);

CREATE TABLE SystemUserRole (
    idUser INTEGER,
    idUserRole INTEGER
);
ALTER TABLE SystemUserRole ADD CONSTRAINT pk_SystemUserRole PRIMARY KEY (idUser, idUserRole);
ALTER TABLE SystemUserRole ADD CONSTRAINT fk_SystemUser FOREIGN KEY (idUser) REFERENCES SystemUser(idUser);
ALTER TABLE SystemUserRole ADD CONSTRAINT fk_SystemRole FOREIGN KEY (idUserRole) REFERENCES SystemRole(idUserRole);

CREATE TABLE Occupation (
    idOccupation INTEGER PRIMARY KEY,
    designation VARCHAR(255),
    salary NUMBER(10,2)
);

CREATE TABLE Employee (
    idEmployee INTEGER PRIMARY KEY,
    idPerson INTEGER,
    idUser INTEGER
);
ALTER TABLE Employee ADD CONSTRAINT fk_EmployeePerson FOREIGN KEY (idPerson) REFERENCES Person(idPerson);
ALTER TABLE Employee ADD CONSTRAINT fk_EmployeeUser FOREIGN KEY (idUser) REFERENCES SystemUser(idUser);

CREATE TABLE EmployeeOccupation (
    idEmployee INTEGER,
    idOccupation INTEGER
);
ALTER TABLE EmployeeOccupation ADD CONSTRAINT pk_EmployeeOccupation PRIMARY KEY (idEmployee, idOccupation);
ALTER TABLE EmployeeOccupation ADD CONSTRAINT fk_EmployeeOccupationEmployee FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee);
ALTER TABLE EmployeeOccupation ADD CONSTRAINT fk_EmployeeOccupationOccupation FOREIGN KEY (idOccupation) REFERENCES Occupation(idOccupation);

CREATE TABLE VehicleType (
    idVehicleType INTEGER PRIMARY KEY,
    designation VARCHAR(50)
);

CREATE TABLE Vehicle (
    idVehicle INTEGER PRIMARY KEY,
    idVehicleType INTEGER
);
ALTER TABLE Vehicle ADD CONSTRAINT fk_VehicleType FOREIGN KEY(idVehicletype) REFERENCES VehicleType(idVehicleType);

CREATE TABLE VesselType (
    idVesselType INTEGER PRIMARY KEY,
    designation VARCHAR(100)
);

CREATE TABLE Ship (
    idVehicle INTEGER PRIMARY KEY,
    MMSI INTEGER UNIQUE,
    IMO VARCHAR(10) UNIQUE,
    shipName VARCHAR(100),
    callSign VARCHAR(15) UNIQUE,
    vesselType INTEGER,
    length NUMBER(10,2),
    width NUMBER(10,2),
    capacity INTEGER,
    draft NUMBER(10,2)
);
ALTER TABLE Ship ADD CONSTRAINT fk_ShipVesselType FOREIGN KEY (vesselType) REFERENCES VesselType(idVesselType);
ALTER TABLE Ship ADD CONSTRAINT fk_ShipVehicle FOREIGN KEY (idVehicle) REFERENCES Vehicle(idVehicle);

CREATE TABLE Truck (
    idVehicle INTEGER PRIMARY KEY,
    idEmployee INTEGER
);
ALTER TABLE Truck ADD CONSTRAINT fk_TruckVehicle FOREIGN KEY (idVehicle) REFERENCES Vehicle(idVehicle);
ALTER TABLE Truck ADD CONSTRAINT fk_TruckEmployee FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee);

CREATE TABLE ContainerType (
    idContainerType INTEGER PRIMARY KEY,
    designation VARCHAR(100)
);

CREATE TABLE Container (
    idContainer VARCHAR(11) PRIMARY KEY,
    tare NUMBER(10,2),
    payload NUMBER(10,2),
    gross NUMBER(10,2),
    isoCode VARCHAR(4),
    refrigerated INTEGER,
    temperature NUMBER(6,2),
    idContainerType INTEGER
);
ALTER TABLE Container ADD CONSTRAINT fk_ContainerType FOREIGN KEY (idContainerType) REFERENCES ContainerType(idContainerType);

CREATE TABLE Certificate (
    idCertificate INTEGER PRIMARY KEY,
    certificateName VARCHAR(100)
);

CREATE TABLE ContainerCertificate (
    idContainer VARCHAR(11),
    idCertificate INTEGER
);
ALTER TABLE ContainerCertificate ADD CONSTRAINT pk_ContainerCertificate PRIMARY KEY (idContainer, idCertificate);
ALTER TABLE ContainerCertificate ADD CONSTRAINT fk_Container FOREIGN KEY (idContainer) REFERENCES Container(idContainer);
ALTER TABLE ContainerCertificate ADD CONSTRAINT fk_Certificate FOREIGN KEY (idCertificate) REFERENCES Certificate(idCertificate);

CREATE TABLE Client (
    idClient INTEGER PRIMARY KEY,
    idUser INTEGER
);
ALTER TABLE Client ADD CONSTRAINT fk_ClientPerson FOREIGN KEY (idClient) REFERENCES Person(idPerson);
ALTER TABLE Client ADD CONSTRAINT fk_ClientUser FOREIGN KEY (idUser) REFERENCES SystemUser(idUser);

CREATE TABLE Leasing (
    idClient INTEGER,
    idContainer VARCHAR(11),
    leasingDate TIMESTAMP
);
ALTER TABLE Leasing ADD CONSTRAINT pk_Leasing PRIMARY KEY(idClient, idContainer, leasingDate);
ALTER TABLE Leasing ADD CONSTRAINT fk_LeasingClient FOREIGN KEY (idClient) REFERENCES Client(idClient);
ALTER TABLE Leasing ADD CONSTRAINT fk_LeasingContainer FOREIGN KEY (idContainer) REFERENCES Container(idContainer);

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
ALTER TABLE ShipPosition ADD CONSTRAINT fk_ShipPositionShip FOREIGN KEY (idShip) REFERENCES Ship(idVehicle);
ALTER TABLE ShipPosition ADD CONSTRAINT fk_ShipPositionPosition FOREIGN KEY (idPosition) REFERENCES Position(idPosition);

CREATE TABLE Continent (
    alpha2CodeContinent VARCHAR(2) PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE Country (
    alpha2CodeCountry VARCHAR(2) PRIMARY KEY,
    alpha3CodeCountry VARCHAR(3) UNIQUE,        
    idPosition INTEGER UNIQUE,
    alpha2CodeContinent VARCHAR(2),
    name VARCHAR(100),
    capital VARCHAR(100),
    population NUMBER(6,2)
);
ALTER TABLE Country ADD CONSTRAINT fk_CountryPosition FOREIGN KEY (idPosition) REFERENCES Position(idPosition);
ALTER TABLE Country ADD CONSTRAINT fk_CountryContinent FOREIGN KEY (alpha2CodeContinent) REFERENCES Continent(alpha2CodeContinent);

CREATE TABLE Border(
    alpha2CodeCountry1 VARCHAR(2),
    alpha2CodeCountry2 VARCHAR(2)
);
ALTER TABLE Border ADD CONSTRAINT pk_Border PRIMARY KEY (alpha2CodeCountry1, alpha2CodeCountry2);
ALTER TABLE Border ADD CONSTRAINT fk_BorderCountry1 FOREIGN KEY (alpha2CodeCountry1) REFERENCES Country(alpha2CodeCountry);
ALTER TABLE Border ADD CONSTRAINT fk_BorderCountry2 FOREIGN KEY (alpha2CodeCountry2) REFERENCES Country(alpha2CodeCountry);

CREATE TABLE PortWarehouseType (
    idPortWarehouseType CHAR(1) PRIMARY KEY,
    designation VARCHAR(50)
);

CREATE TABLE PortWarehouse (
    idPortWarehouse INTEGER PRIMARY KEY,
    idPosition INTEGER UNIQUE,
    alpha2CodeCountry VARCHAR(2),
    name VARCHAR(100),
    capacity INTEGER,
    idPortWarehouseType CHAR
);
ALTER TABLE PortWarehouse ADD CONSTRAINT fk_PortWarehousePosition FOREIGN KEY (idPosition) REFERENCES Position(idPosition);
ALTER TABLE PortWarehouse ADD CONSTRAINT fk_PortWarehouseCountry FOREIGN KEY (alpha2CodeCountry) REFERENCES Country(alpha2CodeCountry);
ALTER TABLE PortWarehouse ADD CONSTRAINT fk_PortWarehouseType FOREIGN KEY (idPortWarehouseType) REFERENCES PortWarehouseType(idPortWarehouseType);

CREATE TABLE Seadist (
    idPortWarehouseFrom INTEGER,
    idPortWarehouseTo INTEGER,
    seadist INTEGER
);
ALTER TABLE Seadist ADD CONSTRAINT pk_Seadist PRIMARY KEY (idPortWarehouseFrom, idPortWarehouseTo);
ALTER TABLE Seadist ADD CONSTRAINT fk_PortWarehouseFrom FOREIGN KEY (idPortWarehouseFrom) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE Seadist ADD CONSTRAINT fk_PortWarehouseTo FOREIGN KEY (idPortWarehouseTo) REFERENCES PortWarehouse(idPortWarehouse);

CREATE TABLE PortWarehouseStaff (
    idPortWarehouse INTEGER,
    idEmployee INTEGER
);
ALTER TABLE PortWarehouseStaff ADD CONSTRAINT pk_PortWarehouseStaff PRIMARY KEY (idPortWarehouse, idEmployee);
ALTER TABLE PortWarehouseStaff ADD CONSTRAINT fk_StaffPortWarehouse FOREIGN KEY (idPortWarehouse) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE PortWarehouseStaff ADD CONSTRAINT fk_StaffEmployee FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee);

CREATE TABLE PortWarehouseConnection (
    idWarehouse INTEGER PRIMARY KEY,
    idPort INTEGER
);
ALTER TABLE PortWarehouseConnection ADD CONSTRAINT fk_PortWarehouseConnectionWarehouse FOREIGN KEY (idWarehouse) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE PortWarehouseConnection ADD CONSTRAINT fk_PortWarehouseConnectionPort FOREIGN KEY (idPort) REFERENCES PortWarehouse(idPortWarehouse);

CREATE TABLE Trip (
    idTrip INTEGER PRIMARY KEY,
    idPortWarehouseDeparture INTEGER,
    idPortWarehouseArrival INTEGER,
    idVehicle INTEGER,
    endDate TIMESTAMP
);
ALTER TABLE Trip ADD CONSTRAINT fk_TripPortWarehouse1 FOREIGN KEY (idPortWarehouseDeparture) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE Trip ADD CONSTRAINT fk_TripPortWarehouse2 FOREIGN KEY (idPortWarehouseArrival) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE Trip ADD CONSTRAINT fk_TripVehicle FOREIGN KEY (idVehicle) REFERENCES Vehicle(idVehicle);

CREATE TABLE Crew (
    idTrip INTEGER,
    idEmployee INTEGER
);
ALTER TABLE Crew ADD CONSTRAINT pk_Crew PRIMARY KEY (idTrip, idEmployee);
ALTER TABLE Crew ADD CONSTRAINT fk_CrewTrip FOREIGN KEY (idTrip) REFERENCES Trip(idTrip);
ALTER TABLE Crew ADD CONSTRAINT fk_CrewEmployee FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee);

CREATE TABLE CargoManifestType (
    idCargoManifestType INTEGER PRIMARY KEY,
    designation VARCHAR(100)
);

CREATE TABLE CargoManifest (
    idCargoManifest INTEGER PRIMARY KEY,
    content VARCHAR(255),
    elaborationDate TIMESTAMP,
    idCargoManifestType INTEGER
);
ALTER TABLE CargoManifest ADD CONSTRAINT fk_CargoManifestType FOREIGN KEY (idCargoManifestType) REFERENCES CargoManifestType(idCargoManifestType);

CREATE TABLE ArrivalDeparture (
    idTrip INTEGER,
    idPortWarehouseDeparture INTEGER,
    idPortWarehouseArrival INTEGER,
    departureDate TIMESTAMP,
    arrivalDate TIMESTAMP,
    estimatedArrivalDate TIMESTAMP,
    idCargoManifestLoad INTEGER,
    idCargoManifestUnload INTEGER
);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT pk_ArrivalDeparture PRIMARY KEY (idTrip, idPortWarehouseDeparture, idPortWarehouseArrival);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT fk_ArrivalDepartureTrip FOREIGN KEY (idTrip) REFERENCES Trip(idTrip);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT fk_ArrivalDeparturePortWarehouse1 FOREIGN KEY (idPortWarehouseDeparture) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT fk_ArrivalDeparturePortWarehouse2 FOREIGN KEY (idPortWarehouseArrival) REFERENCES PortWarehouse(idPortWarehouse);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT fk_ArrivalDepartureCargoManifest1 FOREIGN KEY (idCargoManifestLoad) REFERENCES CargoManifest(idCargoManifest);
ALTER TABLE ArrivalDeparture ADD CONSTRAINT fk_ArrivalDepartureCargoManifest2 FOREIGN KEY (idCargoManifestUnload) REFERENCES CargoManifest(idCargoManifest);

CREATE TABLE Summary (
    idSummary INTEGER PRIMARY KEY,
    idTrip INTEGER,
    idPortWarehouseDeparture INTEGER,
    idPortWarehouseArrival INTEGER,
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
ALTER TABLE Summary ADD CONSTRAINT fk_SummaryArrivalDeparture FOREIGN KEY (idTrip, idPortWarehouseDeparture, idPortWarehouseArrival) REFERENCES ArrivalDeparture(idTrip, idPortWarehouseDeparture, idPortWarehouseArrival);

CREATE TABLE ManifestContainer (
    idCargoManifest INTEGER,
    idContainer VARCHAR(11),
    containerPositionX INTEGER,
    containerPositionY INTEGER,
    containerPositionZ INTEGER
);
ALTER TABLE ManifestContainer ADD CONSTRAINT pk_ManifestContainer PRIMARY KEY (idCargoManifest, idContainer);
ALTER TABLE ManifestContainer ADD CONSTRAINT fk_ManifestContainerCargoManifest FOREIGN KEY (idCargoManifest) REFERENCES CargoManifest(idCargoManifest);
ALTER TABLE ManifestContainer ADD CONSTRAINT fk_ManifestContainerContainer FOREIGN KEY (idContainer) REFERENCES Container(idContainer);

CREATE TABLE AuditTrail (
    idAuditTrail INTEGER PRIMARY KEY,
    idCargoManifest INTEGER,
    idContainer VARCHAR(11),
    idUser INTEGER,
    dateTime TIMESTAMP,
    operationType VARCHAR(50)
);
ALTER TABLE AuditTrail ADD CONSTRAINT fk_AuditTrailUser FOREIGN KEY (idUser) REFERENCES SystemUser(idUser);