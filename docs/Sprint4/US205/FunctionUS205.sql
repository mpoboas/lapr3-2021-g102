CREATE OR REPLACE FUNCTION fnc_get_containers_to_be_offloaded(in_idShip vehicle.idVehicle%type, in_date TIMESTAMP) RETURN sys_refcursor
    AS
    c_to_be_offloaded sys_refcursor;

    v_id_trip trip.idtrip%type;
    v_id_port portwarehouse.idportwarehouse%type;
    v_port_name portwarehouse.name%type;
    v_id_cargo_manifest_unload cargomanifest.idcargomanifest%type;

BEGIN

SELECT idTrip INTO v_id_trip
FROM (SELECT *
      FROM TRIP
      WHERE idVehicle = in_idShip)
WHERE ROWNUM = 1;


SELECT idCargoManifestUnload INTO v_id_cargo_manifest_unload
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

OPEN c_to_be_offloaded
    FOR
SELECT *
FROM MANIFESTCONTAINER
WHERE idCargoManifest = v_id_cargo_manifest_unload;

dbms_output.Put_line('List of containers to be offloaded in ' || v_port_name || ':'); --display the port name
RETURN (c_to_be_offloaded);

end;

