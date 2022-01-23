SET serveroutput ON;
Declare
c_cursor SYS_REFCURSOR;
    c_containers SYS_REFCURSOR;
    v_estimatedArrivalDate TIMESTAMP;
    v_idTrip INTEGER;
    v_idPortWarehouseArrival INTEGER;
    v_idCargoManifest INTEGER;
    v_cargoManifestType INTEGER;
    v_idVehicle INTEGER;
    v_idVehicleType INTEGER;
    v_vehicleName VARCHAR(100);
    v_totalContainers INTEGER;

    --v_container c_containers%ROWTYPE;
    v_idCargoManifestContainer INTEGER;
    v_idContainer VARCHAR(11);
    v_containerPosX INTEGER;
    v_containerPosY INTEGER;
    v_containerPosZ INTEGER;

    v_idEmployee INTEGER;
    v_idPort INTEGER;
    v_portName VARCHAR(100);
    v_portManagerName VARCHAR(255);
    v_username VARCHAR(255) := 'joaosilva12';
    v_date TIMESTAMP := TO_TIMESTAMP('01.12.21 00:00:00,000');
    v_7_days_from_date TIMESTAMP := v_date + INTERVAL '7' DAY;


BEGIN
    -- Gets the employee's ID
    SELECT IDEMPLOYEE INTO v_idEmployee
    FROM EMPLOYEE
    WHERE IDUSER = (SELECT IDUSER
                    FROM SYSTEMUSER
                    WHERE USERNAME = v_username);

    -- Gets the employee's (port manager) port
    SELECT IDPORTWAREHOUSE INTO v_idPort
    FROM PORTWAREHOUSESTAFF
    WHERE IDEMPLOYEE = v_idEmployee;

    -- Get port's name
    SELECT NAME INTO v_portName
    FROM PORTWAREHOUSE
    WHERE IDPORTWAREHOUSE = v_idPort;

    -- Get Port Manager's name
    SELECT NAME INTO v_portManagerName
    FROM EMPLOYEE
    WHERE IDEMPLOYEE = v_idEmployee;

    dbms_output.put_line('Loading/unloading map for the port ' || v_portName || ' between ' || v_date || ' and ' || v_7_days_from_date);
    dbms_output.put_line('Port Manager: ' || v_portManagerName);
    c_cursor := FNC_GET_LOADING_UNLOADING_MAP(v_username, TO_TIMESTAMP('01.12.21 00:00:00,000'));
    LOOP
        FETCH c_cursor INTO v_estimatedArrivalDate, v_idTrip, v_idPortWarehouseArrival, v_idCargoManifest, v_cargoManifestType;
            EXIT WHEN c_cursor%NOTFOUND;

        SELECT IDVEHICLE INTO v_idVehicle FROM TRIP WHERE IDTRIP = v_idTrip;
        SELECT IDVEHICLETYPE INTO v_idVehicleType FROM VEHICLE WHERE IDVEHICLE = v_idVehicle;
        IF (v_idVehicleType= 1) THEN
        SELECT SHIPNAME INTO v_vehicleName FROM SHIP WHERE IDVEHICLE = v_idVehicle;
        ELSE v_vehicleName := CONCAT('TRUCK ',v_idVehicle);
        END IF;

        c_containers := FNC_GET_MANIFEST_CONTAINERS(v_idCargoManifest);

        CASE v_idCargoManifest
            WHEN 0 THEN
                NULL;
        ELSE
            CASE v_cargoManifestType
                WHEN 1 THEN dbms_output.put_line('[TRIP ' || v_idTrip || '] ' || v_estimatedArrivalDate || '| LOAD | ' ||  v_portName || ' -> ' || v_vehicleName); -- || ' | CONTAINERS: '|| v_totalContainers);
                    LOOP
                        FETCH c_containers INTO v_idCargoManifestContainer, v_idContainer, v_containerPosX, v_containerPosY, v_containerPosZ;
                        EXIT WHEN c_containers%NOTFOUND;
                        dbms_output.put_line('          [CM '|| v_idCargoManifestContainer ||'][' || v_idContainer || '] POSITION: ' || v_containerPosX || ', ' ||  v_containerPosY || ', ' || v_containerPosZ);
                    END LOOP;
            WHEN 2 THEN dbms_output.put_line('[TRIP ' || v_idTrip || '] ' || v_estimatedArrivalDate || '| UNLOAD | ' || v_vehicleName  || ' -> ' || v_portName); -- || ' | CONTAINERS: '|| v_totalContainers);
                    LOOP
                        FETCH c_containers INTO v_idCargoManifestContainer, v_idContainer, v_containerPosX, v_containerPosY, v_containerPosZ;
                        EXIT WHEN c_containers%NOTFOUND;
                        dbms_output.put_line('          [CM '|| v_idCargoManifestContainer ||'][' || v_idContainer || '] POSITION: ' || v_containerPosX || ', ' ||  v_containerPosY || ', ' || v_containerPosZ);
                    END LOOP;
            END CASE;
        END CASE;
    END LOOP;
CLOSE c_cursor;
END;