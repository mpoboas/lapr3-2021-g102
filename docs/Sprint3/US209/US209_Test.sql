-- Test US209 - Get occupancy rate of a given ship at a given moment
SET SERVEROUTPUT ON;
DECLARE
    v_occupancy_rate NUMBER(6, 2) := 0;
    v_id_cargo_manifest INTEGER;
    v_id_ship INTEGER;
BEGIN
    v_id_ship := fnc_get_ship_id_by_mmsi(316005667);
    dbms_output.put_line ('Ship Id --> ' || v_id_ship);
    v_id_cargo_manifest := fnc_get_cargo_manifest_id_by_date_time('2021-12-01 00:30');
    dbms_output.put_line ('Cargo Manifest Id --> ' || v_id_cargo_manifest);
    v_occupancy_rate := fnc_get_ship_occupancy_rate(v_id_ship, v_id_cargo_manifest);
    dbms_output.put_line ('Occupancy rate --> ' || v_occupancy_rate || '%');
END;