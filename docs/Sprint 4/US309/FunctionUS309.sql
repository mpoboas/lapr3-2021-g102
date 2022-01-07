CREATE OR REPLACE TRIGGER trg_available_ship_warning
BEFORE INSERT OR UPDATE
    ON user_g102.arrivalDeparture
    FOR EACH ROW
DECLARE
    v_idTrip integer;
    v_loadDate timestamp;
    v_unloadDate timestamp;
    v_endDate timestamp;
    v_shipName varchar(100);
    v_idShip integer;
BEGIN
    v_idTrip := :new.IDTRIP;

    -- Get the ship's ID
    SELECT IDVEHICLE INTO v_idShip
    FROM TRIP
    WHERE TRIP.IDTRIP = :new.IDTRIP;

    -- Get the ship's name
    SELECT SHIP.SHIPNAME INTO v_shipName
    FROM SHIP
    WHERE SHIP.IDVEHICLE = v_idShip;

    -- Date of the last loading cargo manifest
    SELECT MAX(CARGOMANIFEST.ELABORATIONDATE) INTO v_loadDate
    FROM CARGOMANIFEST
    WHERE IDCARGOMANIFEST = :new.IDCARGOMANIFESTLOAD;

    -- Date of the last unloading cargo manifest
    SELECT MAX(CARGOMANIFEST.ELABORATIONDATE) INTO v_unloadDate
    FROM CARGOMANIFEST
    WHERE IDCARGOMANIFEST = :new.IDCARGOMANIFESTUNLOAD;

    -- End date of the trip
    SELECT ARRIVALDATE INTO v_endDate
    FROM ARRIVALDEPARTURE
    WHERE IDTRIP = v_idTrip AND ARRIVALDEPARTURE.ARRIVALDATE = (SELECT MAX(ARRIVALDATE)
                                                                FROM ARRIVALDEPARTURE
                                                                WHERE IDTRIP = v_idTrip);

    IF ( v_endDate > v_loadDate OR v_endDate > v_unloadDate)
    THEN RAISE_APPLICATION_ERROR(-20309, 'The ship ' || v_shipName || ' is occupied.');
    END IF;
END;