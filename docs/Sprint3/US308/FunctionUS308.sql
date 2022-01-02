create or replace TRIGGER trg_cargo_ship_warning
BEFORE INSERT OR UPDATE
                            ON user_g102.arrivalDeparture
                            FOR EACH ROW
DECLARE
v_idShip integer;
    v_capacity integer;
    v_totalContainers integer;
    v_loadedContainers integer;
    v_unloadedContainers integer;
BEGIN
SELECT TRIP.IDVEHICLE INTO v_idShip
FROM TRIP
WHERE TRIP.IDTRIP = :new.IDTRIP;

SELECT SHIP.CAPACITY INTO v_capacity
FROM SHIP
WHERE SHIP.IDVEHICLE = v_idShip;

SELECT COUNT(*) INTO v_loadedContainers
FROM TRIP, ARRIVALDEPARTURE, MANIFESTCONTAINER
WHERE ARRIVALDEPARTURE.IDTRIP = :new.IDTRIP AND TRIP.IDTRIP = :new.IDTRIP
  AND TRIP.IDVEHICLE = v_idShip
  AND MANIFESTCONTAINER.IDCARGOMANIFEST = ARRIVALDEPARTURE.IDCARGOMANIFESTLOAD;

SELECT COUNT(*) INTO v_unloadedContainers
FROM TRIP, ARRIVALDEPARTURE, MANIFESTCONTAINER
WHERE ARRIVALDEPARTURE.IDTRIP = :new.IDTRIP AND TRIP.IDTRIP = :new.IDTRIP
  AND TRIP.IDVEHICLE = v_idShip
  AND MANIFESTCONTAINER.IDCARGOMANIFEST = ARRIVALDEPARTURE.IDCARGOMANIFESTUNLOAD;

v_totalContainers := v_loadedContainers - v_unloadedContainers;

    IF (v_totalContainers > v_capacity) THEN RAISE_APPLICATION_ERROR(-308,'Cargo Manifest exceeds the ship capacity');
END IF;
END;