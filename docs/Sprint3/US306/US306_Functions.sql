CREATE OR REPLACE FUNCTION fnc_get_each_warehouse_occupancy_rate(p_id_port_manager INTEGER) RETURN SYS_REFCURSOR
IS
c_port_warehouse_occupancy_rate SYS_REFCURSOR;
BEGIN
    OPEN c_port_warehouse_occupancy_rate
    FOR
        -- Select all the warehouses managed by the given port manager, and its respective occupancy rate
        SELECT idPortWarehouse, Warehouse_Name,(Number_containers_entered - Number_containers_left)/Warehouse_Capacity * 100 AS Warehouse_occupancy_rate
        FROM (SELECT PortWarehouse.idPortWarehouse, PortWarehouse.name AS Warehouse_Name, PortWarehouse.capacity AS Warehouse_Capacity,
            (SELECT COUNT(*) FROM (SELECT Container.idContainer
                FROM Container, CargoManifest, CargoManifestType, ManifestContainer, ArrivalDeparture, Trip, Vehicle
                WHERE CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND ManifestContainer.idContainer = Container.idContainer
                AND CargoManifest.idCargoManifestType = CargoManifestType.idCargoManifestType AND CargoManifest.idCargoManifestType = 2 AND CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestUnload
                AND ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Vehicle.idVehicle AND ArrivalDeparture.arrivalDate IS NOT NULL
                AND PortWarehouse.idPortWarehouseType = 'W' AND ArrivalDeparture.idPortWarehouseArrival = PortWarehouse.idPortWarehouse))
                AS Number_containers_entered,
            (SELECT COUNT(*) FROM (SELECT Container.idContainer
                FROM Container, CargoManifest, CargoManifestType, ManifestContainer, ArrivalDeparture, Trip, Vehicle
                WHERE CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND ManifestContainer.idContainer = Container.idContainer
                AND CargoManifest.idCargoManifestType = CargoManifestType.idCargoManifestType AND CargoManifest.idCargoManifestType = 1 AND CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestLoad
                AND ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Vehicle.idVehicle AND ArrivalDeparture.arrivalDate IS NOT NULL
                AND PortWarehouse.idPortWarehouseType = 'W' AND ArrivalDeparture.idPortWarehouseDeparture = PortWarehouse.idPortWarehouse))
                AS Number_containers_left
        FROM PortWarehouse, PortWarehouseStaff, Employee, PortWarehouseConnection, Occupation, EmployeeOccupation, PortWarehouseType
        WHERE PortWarehouseType.idPortWarehouseType = PortWarehouse.idPortWarehouseType AND PortWarehouseType.idPortWarehouseType = 'W'
        AND PortWarehouseStaff.idEmployee = Employee.idEmployee AND PortWarehouseStaff.idEmployee = p_id_port_manager
        AND Employee.idEmployee = EmployeeOccupation.idEmployee AND EmployeeOccupation.idOccupation = Occupation.idOccupation AND Occupation.idOccupation = 1
        AND PortWarehouseConnection.idPort = PortWarehouseStaff.idPortWarehouse AND PortWarehouse.idPortWarehouse = PortWarehouseConnection.idWarehouse);
        
    RETURN c_port_warehouse_occupancy_rate;
END;

CREATE OR REPLACE FUNCTION fnc_get_containers_leaving_next_30_days(p_id_port_warehouse INTEGER) RETURN SYS_REFCURSOR
IS
c_num_containers_leaving SYS_REFCURSOR;
v_current_timestamp TIMESTAMP;
v_next_30_days_timestamp TIMESTAMP;
BEGIN
    -- Selects current date and time
    SELECT CURRENT_TIMESTAMP INTO v_current_timestamp
    FROM dual;
    -- Adds 30 days to that date and time
    v_next_30_days_timestamp := v_current_timestamp + INTERVAL '30' DAY;
    
    OPEN c_num_containers_leaving
    FOR 
        -- Selects all the containers leaving the warehouse between both dates
        SELECT DISTINCT Container.idContainer, Container.gross AS Gross_Weight, ContainerType.designation AS Container_Type
        FROM Container, ContainerType, CargoManifest, CargoManifestType, ManifestContainer, ArrivalDeparture, PortWarehouse
        WHERE CargoManifest.idCargoManifest = ManifestContainer.idCargoManifest AND ManifestContainer.idContainer = Container.idContainer AND Container.idContainerType = ContainerType.idContainerType
        AND CargoManifest.idCargoManifestType = CargoManifestType.idCargoManifestType AND CargoManifest.idCargoManifestType = 1 AND CargoManifest.idCargoManifest = ArrivalDeparture.idCargoManifestLoad
        AND ArrivalDeparture.idPortWarehouseArrival = PortWarehouse.idPortWarehouse AND PortWarehouse.idPortWarehouse = p_id_port_warehouse
        AND ArrivalDeparture.estimatedArrivalDate BETWEEN v_current_timestamp AND v_next_30_days_timestamp;
    RETURN c_num_containers_leaving;
END;