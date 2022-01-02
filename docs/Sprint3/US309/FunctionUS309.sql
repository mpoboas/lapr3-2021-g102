create or replace TRIGGER trg_available_ship_warning
BEFORE INSERT OR UPDATE
    ON user_g102.arrivalDeparture
    FOR EACH ROW
DECLARE
v_idTrip integer;
    v_loadDate timestamp;
    v_unloadDate timestamp;
    v_endDate timestamp;
BEGIN
    v_idTrip := :new.IDTRIP;

    SELECT MAX(CARGOMANIFEST.ELABORATIONDATE) INTO v_loadDate
    FROM CARGOMANIFEST
    WHERE IDCARGOMANIFEST = :new.IDCARGOMANIFESTLOAD;

    SELECT MAX(CARGOMANIFEST.ELABORATIONDATE) INTO v_unloadDate
    FROM CARGOMANIFEST
    WHERE IDCARGOMANIFEST = :new.IDCARGOMANIFESTUNLOAD;

    SELECT MAX(ENDDATE) INTO v_endDate
    FROM ARRIVALDEPARTURE, TRIP, CARGOMANIFEST
    WHERE TRIP.IDTRIP = v_idTrip AND ARRIVALDEPARTURE.IDTRIP = v_idTrip;

    IF ( v_endDate > v_loadDate OR v_endDate > v_unloadDate)
    THEN RAISE_APPLICATION_ERROR(-309, 'The ship is already occupied.');
    END IF;
END;