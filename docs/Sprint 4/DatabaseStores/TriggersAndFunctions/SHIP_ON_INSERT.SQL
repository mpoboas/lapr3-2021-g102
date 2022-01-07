create trigger SHIP_ON_INSERT
    before insert
    on SHIP
    for each row WHEN (new.IDVEHICLE IS NULL)
    DECLARE IDS NUMBER;

BEGIN
    IDS := SHIP_NEW_ID();
    Select IDS
    INTO :new.IDVEHICLE
    FROM dual;
END;