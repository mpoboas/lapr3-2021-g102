-- Test US209 - Get occupancy rate of a given ship at a given moment
SET SERVEROUTPUT ON;
DECLARE
    occupancyRate NUMBER(6, 2) := 0;
    idCargoManifest INTEGER;
    idShip INTEGER;
BEGIN
    idShip := getShipIdByMMSI(112);
    dbms_output.put_line ('Ship Id --> ' || idShip);
    idCargoManifest := getCargoManifestIdByDateTime('03-03-2020 00:00');
    dbms_output.put_line ('Cargo Manifest Id --> ' || idCargoManifest);
    occupancyRate := getShipOccupancyRate(1, idCargoManifest);
    dbms_output.put_line ('Occupancy rate --> ' || occupancyRate || '%');
END;