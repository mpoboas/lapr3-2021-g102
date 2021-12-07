-- Test US208 - Get occupancy rate of a given ship for a given cargo manifest
SET SERVEROUTPUT ON;
DECLARE
    occupancyRate NUMBER(6, 2) := 0;
    idCargoManifest INTEGER;
    idShip INTEGER;
BEGIN
    idShip := getShipIdByMMSI(112);
    dbms_output.put_line ('Ship Id --> ' || idShip);
    occupancyRate := getShipOccupancyRate(1, 1);
    dbms_output.put_line ('Occupancy rate --> ' || occupancyRate || '%');
END;