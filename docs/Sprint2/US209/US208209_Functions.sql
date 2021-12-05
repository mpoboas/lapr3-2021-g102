CREATE OR REPLACE FUNCTION getShipCapacity (idShip INTEGER) RETURN INTEGER
IS
shipCapacity INTEGER := 0;
BEGIN
    SELECT Ship.capacity INTO shipCapacity
    FROM Vehicle, Ship
    WHERE Vehicle.idVehicle = Ship.idVehicle AND Ship.idVehicle = idShip;
    RETURN shipCapacity;
END;

CREATE OR REPLACE FUNCTION getShipIdByMMSI(shipMMSI INTEGER) RETURN INTEGER
IS
shipId INTEGER := 0;
BEGIN
    SELECT Ship.idVehicle INTO shipId 
    FROM Ship 
    WHERE Ship.MMSI = shipMMSI;
    
    RETURN shipId;
END;

CREATE OR REPLACE FUNCTION getCargoManifestIdByDateTime(localDateTimeStr VARCHAR) RETURN INTEGER
IS
cargoManifestId INTEGER;
BEGIN
        SELECT subQuery.idCargoManifest INTO cargoManifestId
        FROM (SELECT CargoManifest.idCargoManifest, CargoManifest.elaborationDate 
        FROM CargoManifest 
        WHERE CargoManifest.elaborationDate <= TO_TIMESTAMP(localDateTimeStr, 'DD-MM-YYYY HH24:mi') 
        ORDER BY CargoManifest.elaborationDate DESC, CargoManifest.idCargoManifest DESC) subQuery
        WHERE ROWNUM = 1;
        
        RETURN cargoManifestId;
END;

CREATE OR REPLACE FUNCTION getCargoManifestDateById(idDesiredCargoManifest INTEGER) RETURN TIMESTAMP
IS
desiredElaborationDate TIMESTAMP;
BEGIN
        SELECT CargoManifest.elaborationDate INTO desiredElaborationDate
        FROM CargoManifest
        WHERE CargoManifest.idCargoManifest = idDesiredCargoManifest;
        
        RETURN desiredElaborationDate;
END;

CREATE OR REPLACE FUNCTION getShipOccupancyRate(idShip INTEGER, idDesiredCargoManifest INTEGER) RETURN NUMBER 
IS
occupancyRate NUMBER(6, 2) := 0;
shipCapacity INTEGER := 0;
numContainersOnBoard INTEGER := 0;
numLoadedContainers INTEGER;
numUnloadedContainers INTEGER;
desiredElaborationDate TIMESTAMP;
BEGIN
        desiredElaborationDate := getCargoManifestDateById(idDesiredCargoManifest);

        SELECT COUNT(*) INTO numLoadedContainers
        FROM CargoManifest, ManifestContainer, Container, ArrivalDeparture, Trip, Vehicle, Ship
        WHERE CargoManifest.elaborationDate <= desiredElaborationDate AND CargoManifest.idCargoManifest <= idDesiredCargoManifest AND CargoManifest.idCargoManifestType = 1 AND (CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestLoad OR CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestUnload)
        AND ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Vehicle.idVehicle AND Vehicle.idVehicle = Ship.idVehicle AND Ship.idVehicle = idShip 
        AND CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND ManifestContainer.idContainer = Container.idContainer;
        
        SELECT COUNT(*) INTO numUnloadedContainers
        FROM CargoManifest, ManifestContainer, Container, ArrivalDeparture, Trip, Vehicle, Ship
        WHERE CargoManifest.elaborationDate <= desiredElaborationDate AND CargoManifest.idCargoManifest <= idDesiredCargoManifest AND CargoManifest.idCargoManifestType = 2 AND (CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestLoad OR CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestUnload)
        AND ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Vehicle.idVehicle AND Vehicle.idVehicle = Ship.idVehicle AND Ship.idVehicle = idShip 
        AND CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND ManifestContainer.idContainer = Container.idContainer; 
        
        
        numContainersOnBoard := numLoadedContainers - numUnloadedContainers;
        dbms_output.put_line ('Number of containers on the ship --> ' || numContainersOnBoard);
        shipCapacity := getShipCapacity(idShip);
        dbms_output.put_line ('Container capacity of the ship --> ' || shipCapacity);
    
        occupancyRate := (numContainersOnBoard / shipCapacity) * 100;
        RETURN occupancyRate;
END;
