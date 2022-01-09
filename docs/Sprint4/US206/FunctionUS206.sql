CREATE OR REPLACE FUNCTION fnc_get_containers_to_be_loaded(in_idShip vehicle.idVehicle%type, in_date TIMESTAMP) RETURN sys_refcursor
    AS
    c_to_be_loaded sys_refcursor;

    v_id_trip trip.idtrip%type;
    v_id_port portwarehouse.idportwarehouse%type;
    v_port_name portwarehouse.name%type;
    v_id_cargo_manifest_load cargomanifest.idcargomanifest%type;

BEGIN

SELECT idTrip INTO v_id_trip
FROM (SELECT *
      FROM TRIP
      WHERE idVehicle = in_idShip)
WHERE ROWNUM = 1;


SELECT idCargoManifestLoad INTO v_id_cargo_manifest_load
FROM (SELECT *
      FROM arrivaldeparture
      WHERE departureDate < in_date AND idTrip = v_id_trip
      ORDER BY departureDate DESC)
WHERE ROWNUM = 1;

SELECT idPortWarehouseArrival INTO v_id_port
FROM (SELECT *
      FROM arrivaldeparture
      WHERE departureDate < in_date AND idTrip = v_id_trip
      ORDER BY departureDate DESC)
WHERE ROWNUM = 1;

SELECT USER_G102.PORTWAREHOUSE.name INTO v_port_name
FROM PORTWAREHOUSE
WHERE idPortWarehouse = v_id_port;

OPEN c_to_be_loaded
    FOR
SELECT *
FROM MANIFESTCONTAINER
WHERE idCargoManifest = v_id_cargo_manifest_load;

dbms_output.Put_line('List of containers to be loaded in ' || v_port_name || ':'); --display the port name
RETURN (c_to_be_loaded);

end;

