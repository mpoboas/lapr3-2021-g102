CREATE OR REPLACE FUNCTION fnc_get_each_ship_number_of_idle_days(p_fleet_manager_username VARCHAR, p_year INTEGER) RETURN SYS_REFCURSOR
IS
v_max_date DATE;
v_username_user INTEGER;
v_year_arrival_departures INTEGER;
v_fleet_manager_ships INTEGER;
c_number_of_idle_days SYS_REFCURSOR;
ex_username_not_valid EXCEPTION;
ex_year_not_valid EXCEPTION;
BEGIN
    -- Checks if the fleet manager username is valid
    SELECT COUNT(*) INTO v_username_user
    FROM Employee, SystemUser, SystemUserRole, SystemRole
    WHERE SystemUser.username = p_fleet_manager_username AND SystemUser.idUser = Employee.idUser
    AND SystemUser.idUser = SystemUserRole.idUser AND SystemRole.idUserRole = SystemUserRole.idUserRole AND SystemRole.idUserRole = 5;

    -- If the parameter is the current year, use current date, otherwise use the last date of that year
    IF p_year = TO_CHAR(SYSDATE, 'YYYY') THEN v_max_date := SYSDATE;
    ELSIF p_year < TO_CHAR(SYSDATE, 'YYYY') THEN v_max_date := TO_DATE(p_year || '12.31');
    END IF;

    -- If the fleet manager username or the year is not valid, an exception is raised
    IF v_username_user = 0 THEN
        RAISE ex_username_not_valid;
    ELSIF v_year_arrival_departures = 0 THEN
        RAISE ex_year_not_valid;
    ELSE

        OPEN c_number_of_idle_days
        FOR
            -- Selects each ship´s idle time in day format, and the corresponding ship name and ship id. 
            SELECT idShip, shipName, (CASE numTripsYear WHEN 0 THEN (v_max_date - TRUNC(v_max_date,'YEAR') + 1) ELSE (v_max_date - TRUNC(v_max_date,'YEAR') + 1) - tripDays END) AS idleDays
            FROM (SELECT idShip, shipName, numTripsYear, SUM((EXTRACT(DAY FROM tripDuration) + EXTRACT(HOUR FROM tripDuration) / 24 + EXTRACT(MINUTE FROM tripDuration) / 60 / 24 + EXTRACT(SECOND FROM tripDuration) / 60 / 60 / 24)) AS tripDays
            FROM (SELECT Ship.idVehicle AS idShip, Ship.shipName AS shipName,
                (SELECT COUNT(*) FROM (SELECT DISTINCT Trip.idTrip FROM ArrivalDeparture, Trip
                WHERE ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Ship.idVehicle
                AND (EXTRACT(YEAR FROM departureDate) = p_year OR EXTRACT(YEAR FROM arrivalDate) = p_year))) AS numTripsYear,
                MAX(ArrivalDeparture.arrivalDate) - MIN(ArrivalDeparture.departureDate) AS tripDuration
            FROM ArrivalDeparture, Trip, Ship, Vehicle, Crew, Employee, SystemUser
            WHERE SystemUser.idUser = Employee.idUser AND SystemUser.username = p_fleet_manager_username AND Employee.idEmployee = Crew.idEmployee AND Crew.idTrip = Trip.idTrip
            AND ArrivalDeparture.idTrip = Trip.idTrip AND Trip.idVehicle = Vehicle.idVehicle AND Vehicle.idVehicle = Ship.idVehicle
            GROUP BY Trip.idTrip, Ship.idVehicle, Ship.shipName)
            GROUP BY idShip, shipName, numTripsYear);
        RETURN c_number_of_idle_days;
    END IF;
    
    -- Exceptions declaration
    EXCEPTION
        WHEN ex_username_not_valid THEN
            raise_application_error(-20001, 'The specified fleet manager username does not exist.');
            RETURN NULL;
        WHEN ex_year_not_valid THEN
            raise_application_error(-20002, 'There are no ship movements associated with the specified year.');
            RETURN NULL;

END;