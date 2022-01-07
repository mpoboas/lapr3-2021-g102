-- Test US208 - Get occupancy rate of a given ship for a given cargo manifest
SET SERVEROUTPUT ON;
DECLARE
    v_occupancy_rate NUMBER(6, 2) := 0;
    v_id_ship INTEGER;
BEGIN
    v_id_ship := fnc_get_ship_id_by_mmsi(316005667);
    dbms_output.put_line ('Ship Id --> ' || v_id_ship);
    v_occupancy_rate := fnc_get_ship_occupancy_rate(v_id_ship, 1);
    dbms_output.put_line ('Occupancy rate --> ' || v_occupancy_rate || '%');
END;