create or replace FUNCTION fnc_get_loading_unloading_map(in_username VARCHAR, in_date TIMESTAMP) RETURN sys_refcursor
    AS
    c_arrival_departures sys_refcursor;

    v_id_employee employee.idemployee%type;
    v_id_port portwarehousestaff.idportwarehouse%type;
BEGIN

    -- Gets the employee's ID
SELECT IDEMPLOYEE INTO v_id_employee
FROM EMPLOYEE
WHERE IDUSER = (SELECT IDUSER
                FROM SYSTEMUSER
                WHERE USERNAME = in_username);

-- Gets the employee (port manager) port
SELECT IDPORTWAREHOUSE INTO v_id_port
FROM PORTWAREHOUSESTAFF
WHERE IDEMPLOYEE = v_id_employee;

-- Gets all the arrival departures between the two dates for the v_id_port
OPEN c_arrival_departures
    FOR
SELECT DISTINCT *
FROM
    (
        SELECT ESTIMATEDARRIVALDATE, IDTRIP, IDPORTWAREHOUSEARRIVAL, IDCARGOMANIFESTLOAD AS "CARGO_MANIFEST", 1 AS "CARGO_MANIFEST_TYPE" FROM ARRIVALDEPARTURE
        UNION ALL
        SELECT ESTIMATEDARRIVALDATE, IDTRIP, IDPORTWAREHOUSEARRIVAL, IDCARGOMANIFESTUNLOAD AS "CARGO_MANIFEST", 2 AS "CARGO_MANIFEST_TYPE" FROM ARRIVALDEPARTURE
    ) DATA
WHERE IDPORTWAREHOUSEARRIVAL = v_id_port
  AND ESTIMATEDARRIVALDATE BETWEEN in_date AND in_date + INTERVAL '7' DAY
ORDER BY ESTIMATEDARRIVALDATE ASC;

RETURN (c_arrival_departures);
END;




CREATE OR REPLACE FUNCTION fnc_get_manifest_containers(in_id_cargo_manifest INTEGER) RETURN sys_refcursor
    AS
    c_containers sys_refcursor;
BEGIN
    -- Gets the containers associated with a certain cargo manifest
OPEN c_containers
    FOR
SELECT *
FROM MANIFESTCONTAINER
WHERE IDCARGOMANIFEST = in_id_cargo_manifest;
RETURN (c_containers);
END;