CREATE OR REPLACE TRIGGER trg_cargo_port_warning
BEFORE INSERT OR UPDATE
    ON user_g102.arrivalDeparture
    FOR EACH ROW
DECLARE
    v_capacity integer;
    v_totalContainers integer;
    v_loadedContainers integer;
    v_unloadedContainers integer;
    v_availableCapacity integer;
    v_containersComingIn integer;
    v_warehouseName varchar(100);
BEGIN
    -- Get the warehouse's capacity
    SELECT PORTWAREHOUSE.CAPACITY INTO v_capacity
    FROM PORTWAREHOUSE
    WHERE PORTWAREHOUSE.IDPORTWAREHOUSE = :new.IDPORTWAREHOUSEARRIVAL;

    -- Get the warehouse's name
    SELECT PORTWAREHOUSE.NAME INTO v_warehouseName
    FROM PORTWAREHOUSE
    WHERE PORTWAREHOUSE.IDPORTWAREHOUSE = :new.IDPORTWAREHOUSEARRIVAL;

    -- Loaded containers (aka containers that left the warehouse aka loaded from the warehouse to the ship)
    SELECT COUNT(*) INTO v_loadedContainers
    FROM ARRIVALDEPARTURE, MANIFESTCONTAINER
    WHERE ARRIVALDEPARTURE.IDTRIP = :new.IDTRIP
    AND ARRIVALDEPARTURE.IDPORTWAREHOUSEARRIVAL = :new.IDPORTWAREHOUSEARRIVAL
    AND MANIFESTCONTAINER.IDCARGOMANIFEST = ARRIVALDEPARTURE.IDCARGOMANIFESTLOAD;

    -- Unloaded containers (aka containers that came into the warehouse aka unloaded from the ship to the warehouse)
    SELECT COUNT(*) INTO v_unloadedContainers
    FROM ARRIVALDEPARTURE, MANIFESTCONTAINER
    WHERE ARRIVALDEPARTURE.IDTRIP = :new.IDTRIP
    AND ARRIVALDEPARTURE.IDPORTWAREHOUSEARRIVAL = :new.IDPORTWAREHOUSEARRIVAL
    AND MANIFESTCONTAINER.IDCARGOMANIFEST = ARRIVALDEPARTURE.IDCARGOMANIFESTUNLOAD;

    -- Containers coming in from the cargo manifest
    SELECT COUNT(*) INTO v_containersComingIn
    FROM MANIFESTCONTAINER, CARGOMANIFEST
    WHERE CARGOMANIFEST.IDCARGOMANIFEST = :new.IDCARGOMANIFESTUNLOAD AND MANIFESTCONTAINER.IDCARGOMANIFEST = :new.IDCARGOMANIFESTUNLOAD;

    v_totalContainers := v_unloadedContainers - v_loadedContainers;
    v_availableCapacity := v_capacity - v_totalContainers;

    IF (v_containersComingIn > v_availableCapacity) THEN RAISE_APPLICATION_ERROR(-20307,'Cargo Manifest '|| :new.IDCARGOMANIFESTUNLOAD || ' exceeds the ' || v_warehouseName || '''s available capacity (' || v_availableCapacity || '/' || v_capacity || ') | Containers coming in: ' || v_containersComingIn);
    END IF;
END;