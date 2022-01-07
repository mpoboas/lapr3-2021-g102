CREATE OR REPLACE FUNCTION fnc_get_ship_capacity(p_id_ship INTEGER) RETURN INTEGER
IS
v_ship_capacity INTEGER := 0;
BEGIN
    SELECT Ship.capacity INTO v_ship_capacity
    FROM Vehicle, Ship
    WHERE Vehicle.idVehicle = Ship.idVehicle AND Ship.idVehicle = p_id_ship;
    RETURN v_ship_capacity;
END;

CREATE OR REPLACE FUNCTION fnc_get_ship_id_by_mmsi(p_ship_mmsi INTEGER) RETURN INTEGER
IS
v_ship_id INTEGER := 0;
BEGIN
    SELECT Ship.idVehicle INTO v_ship_id 
    FROM Ship 
    WHERE Ship.MMSI = p_ship_mmsi;
    
    RETURN v_ship_id;
END;

CREATE OR REPLACE FUNCTION fnc_get_cargo_manifest_id_by_date_time(p_local_date_time_str VARCHAR) RETURN INTEGER
IS
v_cargo_manifest_id INTEGER;
BEGIN
        SELECT subQuery.idCargoManifest INTO v_cargo_manifest_id
        FROM (SELECT CargoManifest.idCargoManifest, CargoManifest.elaborationDate 
        FROM CargoManifest 
        WHERE CargoManifest.elaborationDate <= TO_TIMESTAMP(p_local_date_time_str, 'YYYY-MM-DD HH24:MI') 
        ORDER BY CargoManifest.elaborationDate DESC, CargoManifest.idCargoManifest DESC) subQuery
        WHERE ROWNUM = 1;
        
        RETURN v_cargo_manifest_id;
END;

CREATE OR REPLACE FUNCTION fnc_get_cargo_manifest_date_by_id(p_id_desired_cargo_manifest INTEGER) RETURN TIMESTAMP
IS
v_desired_elaboration_date TIMESTAMP;
BEGIN
        SELECT CargoManifest.elaborationDate INTO v_desired_elaboration_date
        FROM CargoManifest
        WHERE CargoManifest.idCargoManifest = p_id_desired_cargo_manifest;
        
        RETURN v_desired_elaboration_date;
END;

CREATE OR REPLACE FUNCTION fnc_get_ship_occupancy_rate(p_id_ship INTEGER, p_id_desired_cargo_manifest INTEGER) RETURN NUMBER 
IS
v_occupancy_rate NUMBER(6, 2) := 0;
v_ship_capacity INTEGER := 0;
v_num_containers_on_board INTEGER := 0;
v_num_loaded_containers INTEGER;
v_num_unloaded_containers INTEGER;
v_desired_elaboration_date TIMESTAMP;
BEGIN
        v_desired_elaboration_date := fnc_get_cargo_manifest_date_by_id(p_id_desired_cargo_manifest);

        SELECT COUNT(*) INTO v_num_loaded_containers
        FROM CargoManifest, ManifestContainer, Container, ArrivalDeparture, Trip, Vehicle, Ship
        WHERE CargoManifest.elaborationDate <= v_desired_elaboration_date AND CargoManifest.idCargoManifest <= p_id_desired_cargo_manifest
        AND CargoManifest.idCargoManifestType = 1 AND CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestLoad
        AND ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Vehicle.idVehicle AND Vehicle.idVehicle = Ship.idVehicle AND Ship.idVehicle = p_id_ship 
        AND CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND ManifestContainer.idContainer = Container.idContainer;
        
        SELECT COUNT(*) INTO v_num_unloaded_containers
        FROM CargoManifest, ManifestContainer, Container, ArrivalDeparture, Trip, Vehicle, Ship
        WHERE CargoManifest.elaborationDate <= v_desired_elaboration_date AND CargoManifest.idCargoManifest <= p_id_desired_cargo_manifest
        AND CargoManifest.idCargoManifestType = 2 AND CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestUnload
        AND ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Vehicle.idVehicle AND Vehicle.idVehicle = Ship.idVehicle AND Ship.idVehicle = p_id_ship 
        AND CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND ManifestContainer.idContainer = Container.idContainer; 
        
        v_num_containers_on_board := v_num_loaded_containers - v_num_unloaded_containers;
        dbms_output.put_line ('Number of containers on the ship --> ' || v_num_containers_on_board);
        v_ship_capacity := fnc_get_ship_capacity(p_id_ship);
        dbms_output.put_line ('Container capacity of the ship --> ' || v_ship_capacity);
    
        v_occupancy_rate := (v_num_containers_on_board / v_ship_capacity) * 100;
        RETURN v_occupancy_rate;
END;