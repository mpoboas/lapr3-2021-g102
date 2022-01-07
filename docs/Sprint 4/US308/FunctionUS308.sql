CREATE OR REPLACE TRIGGER trg_cargo_ship_warning
BEFORE INSERT OR UPDATE
    ON user_g102.arrivalDeparture
    FOR EACH ROW
DECLARE
    v_idShip integer;
    v_idTrip integer;
    v_capacity integer;
    v_containersOnBoard integer;
    v_loadedContainers integer;
    v_unloadedContainers integer;
    v_availableCapacity integer;
    v_containersComingIn integer;
    v_shipName varchar(100);
BEGIN
    -- Get the ship's ID
    SELECT IDVEHICLE INTO v_idShip
    FROM TRIP
    WHERE TRIP.IDTRIP = :new.IDTRIP;

    -- Get the ship's name
    SELECT SHIP.SHIPNAME INTO v_shipName
    FROM SHIP
    WHERE SHIP.IDVEHICLE = v_idShip;

    -- Get the ship's capacity
    SELECT SHIP.CAPACITY INTO v_capacity
    FROM SHIP
    WHERE SHIP.IDVEHICLE = v_idShip;

    -- Loaded containers (aka containers that were loaded into the ship aka unloaded from the port/warehouse and loaded into the ship)
    SELECT COUNT(*) INTO v_loadedContainers
    FROM TRIP, ARRIVALDEPARTURE, MANIFESTCONTAINER
    WHERE ARRIVALDEPARTURE.IDTRIP = :new.IDTRIP AND TRIP.IDTRIP = :new.IDTRIP
    AND TRIP.IDVEHICLE = v_idShip
    AND MANIFESTCONTAINER.IDCARGOMANIFEST = ARRIVALDEPARTURE.IDCARGOMANIFESTLOAD;

    -- Unloaded containers (aka containers that were unloaded to the port/warehouse aka unloaded from the ship to the port/warehouse)
    SELECT COUNT(*) INTO v_unloadedContainers
    FROM TRIP, ARRIVALDEPARTURE, MANIFESTCONTAINER
    WHERE ARRIVALDEPARTURE.IDTRIP = :new.IDTRIP AND TRIP.IDTRIP = :new.IDTRIP
    AND TRIP.IDVEHICLE = v_idShip
    AND MANIFESTCONTAINER.IDCARGOMANIFEST = ARRIVALDEPARTURE.IDCARGOMANIFESTUNLOAD;

    -- Containers coming in from the cargo manifest (aka containers being loaded into the ship)
    SELECT COUNT(*) INTO v_containersComingIn
    FROM MANIFESTCONTAINER, CARGOMANIFEST
    WHERE MANIFESTCONTAINER.IDCARGOMANIFEST = :new.IDCARGOMANIFESTLOAD AND CARGOMANIFEST.IDCARGOMANIFEST = :new.IDCARGOMANIFESTLOAD;

    v_containersOnBoard := v_loadedContainers - v_unloadedContainers;
    v_availableCapacity := v_capacity - v_containersOnBoard;

    IF (v_containersComingIn > v_availableCapacity) THEN RAISE_APPLICATION_ERROR(-20308,'Cargo Manifest '|| :new.IDCARGOMANIFESTLOAD || ' exceeds the ' || v_shipName || '''s available capacity (' || v_availableCapacity || '/' || v_capacity || ') | Containers coming in: ' || v_containersComingIn);
    END IF;

    EXCEPTION
    WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20381,'Error: No data was found');
END;