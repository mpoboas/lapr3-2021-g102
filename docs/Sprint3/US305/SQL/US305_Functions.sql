CREATE OR REPLACE FUNCTION fnc_get_client_container_route(p_username_client VARCHAR, p_id_container VARCHAR) RETURN SYS_REFCURSOR
IS
v_current_timestamp TIMESTAMP;
v_id_cargo_manifest_load INTEGER;
v_id_trip INTEGER;
v_date_time_departure TIMESTAMP;
v_date_time_arrival TIMESTAMP;
c_route SYS_REFCURSOR;
v_container_is_valid INTEGER;
v_container_is_leased INTEGER;
ex_container_not_valid EXCEPTION;
ex_container_not_leased EXCEPTION;
BEGIN

    -- Check if the container is valid
    SELECT COUNT(*) INTO v_container_is_valid
    FROM Container
    WHERE Container.idContainer = p_id_container;

    -- Check if the container is leased by the specified client
    SELECT COUNT(*) INTO v_container_is_leased
    FROM Container, Client, Leasing, SystemUser
    WHERE Container.idContainer = p_id_container AND Container.idContainer = Leasing.idContainer AND Leasing.idClient = Client.idClient AND Client.idUser = SystemUser.idUser AND SystemUser.username = p_username_client;

    -- If the container is not valid or it´s not leased by the client, an exception is raised 
    IF v_container_is_valid = 0 THEN
        RAISE ex_container_not_valid;
    ELSIF v_container_is_leased = 0 THEN
        RAISE ex_container_not_leased;
    ELSE
        -- Selects the current date and time
        SELECT CURRENT_TIMESTAMP INTO v_current_timestamp
        FROM dual;

        -- Loading cargo manifest id that contains the container
        SELECT subQuery.idCargoManifest INTO v_id_cargo_manifest_load
        FROM (SELECT CargoManifest.idCargoManifest, CargoManifest.elaborationDate - Leasing.leasingDate AS difference
            FROM CargoManifest, ManifestContainer, Container, Leasing, Client, SystemUser, SystemUserRole, SystemRole
            WHERE CargoManifest.idCargoManifestType = 1 
            AND CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND Container.idContainer = ManifestContainer.idContainer AND Container.idContainer = Leasing.idContainer
            AND Container.idContainer = p_id_container AND Leasing.idClient = Client.idClient AND Client.idUser = SystemUser.idUser AND SystemUser.username = p_username_client
            AND SystemUserRole.idUser = SystemUser.idUser AND SystemUserRole.idUserRole = SystemRole.idUserRole AND SystemUserRole.idUserRole = 1
            AND CargoManifest.elaborationDate >= Leasing.leasingDate
            ORDER BY difference) subQuery
        WHERE ROWNUM = 1;

        -- Selects the cargo manifest trip
        SELECT Trip.idTrip INTO v_id_trip
        FROM ArrivalDeparture, Trip, Vehicle
        WHERE ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Vehicle.idVehicle
        AND ArrivalDeparture.idCargoManifestLoad = v_id_cargo_manifest_load;

        -- Selects the date and time of departure from source
        SELECT ArrivalDeparture.departureDate INTO v_date_time_departure
        FROM ArrivalDeparture, Trip
        WHERE ArrivalDeparture.idTrip = Trip.idTrip AND ArrivalDeparture.idTrip = v_id_trip AND ArrivalDeparture.idCargoManifestLoad = v_id_cargo_manifest_load;

        -- Selects the date and time of estimated arrival to the destination
        SELECT subQuery.estimatedArrivalDate INTO v_date_time_arrival
        FROM (SELECT ArrivalDeparture.estimatedArrivalDate
        FROM ArrivalDeparture, Trip, CargoManifest, CargoManifestType, ManifestContainer, Container
        WHERE ArrivalDeparture.idTrip = Trip.idTrip AND ArrivalDeparture.idTrip = v_id_trip
        AND ArrivalDeparture.estimatedArrivalDate > (SELECT ArrivalDeparture.estimatedArrivalDate FROM ArrivalDeparture WHERE ArrivalDeparture.departureDate = v_date_time_departure)
        AND ArrivalDeparture.idCargoManifestUnload = CargoManifest.idCargoManifest
        AND CargoManifest.idCargoManifestType = CargoManifestType.idCargoManifestType AND CargoManifestType.idCargoManifestType = 2
        AND CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND ManifestContainer.idContainer = Container.idContainer
        AND Container.idContainer = p_id_container
        ORDER BY ArrivalDeparture.estimatedArrivalDate) subQuery
        WHERE ROWNUM = 1;

        OPEN c_route
        FOR 
            -- Selects all the movements of the vehicle that makes/made the transportation of the container
            SELECT DISTINCT JoinnedArrivalDeparture.idPortWarehouseDeparture, JoinnedArrivalDeparture.idPortWarehouseArrival, JoinnedArrivalDeparture.departureName, JoinnedArrivalDeparture.arrivalName, JoinnedArrivalDeparture.departureDate, JoinnedArrivalDeparture.arrivalDate, JoinnedArrivalDeparture.estimatedArrivalDate, VehicleType.designation AS Mean_Of_Transport
            FROM (SELECT DISTINCT ArrivalDeparture.*, p1.name AS departureName, p2.name AS arrivalName
                FROM ArrivalDeparture
                INNER JOIN PortWarehouse p1 ON ArrivalDeparture.idPortWarehouseDeparture = p1.idPortWarehouse 
                INNER JOIN PortWarehouse p2 ON ArrivalDeparture.idPortWarehouseArrival = p2.idPortWarehouse
                INNER JOIN CargoManifest ON ArrivalDeparture.idCargoManifestLoad = CargoManifest.idCargoManifest
                INNER JOIN CargoManifest ON ArrivalDeparture.idCargoManifestUnload = CargoManifest.idCargoManifest) JoinnedArrivalDeparture,
                Trip, CargoManifest, ManifestContainer, Vehicle, VehicleType
            WHERE JoinnedArrivalDeparture.idTrip = v_id_trip AND JoinnedArrivalDeparture.idTrip = Trip.idTrip
            AND Trip.idVehicle = Vehicle.idVehicle AND Vehicle.idVehicleType = VehicleType.idVehicleType
            AND CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest
            AND JoinnedArrivalDeparture.departureDate IS NOT NULL
            AND JoinnedArrivalDeparture.departureDate BETWEEN v_date_time_departure AND v_current_timestamp
            AND JoinnedArrivalDeparture.estimatedArrivalDate BETWEEN v_date_time_departure AND v_date_time_arrival
            ORDER BY JoinnedArrivalDeparture.departureDate;
        RETURN c_route;
    END IF;

    -- Exceptions declaration
    EXCEPTION
        WHEN ex_container_not_valid THEN
            raise_application_error(-20001, 'The specified container does not exist.');
            RETURN NULL;
        WHEN ex_container_not_leased THEN
            raise_application_error(-20002, 'The specified container is not leased by the specified client.');
            RETURN NULL;
END;