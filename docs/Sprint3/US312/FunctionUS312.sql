create or replace FUNCTION fnc_get_container_status(p_containerID container.idcontainer%TYPE, p_clientName VARCHAR2) Return varchar 
AS
    v_cargo_manifest cargomanifest.idcargomanifest%TYPE;
    v_cargo_m_type cargomanifest.idcargomanifesttype%type;
    
    v_count INTEGER;
    v_client INTEGER;
    v_client_id_leased INTEGER;

    v_ship_name ship.shipname%type;
    v_port_name portwarehouse.name%type;

    m_result varchar(100);

    ex_container_not_found exception;
    ex_container_not_leased exception;
    ex_container_not_leased_by_client exception;

BEGIN
    Select count(idcontainer) into v_count
    from container
    where idcontainer = p_containerID;

    IF v_count = 0
    THEN
       raise ex_container_not_found;
    END IF;



    v_count:=0;

    Select count(idContainer) into v_count
    from leasing where idcontainer = p_containerID;

    IF v_count = 0
    THEN
       raise ex_container_not_leased;
    END IF;



    Select idperson into v_client
    from person
    where name = p_clientName;

    Select p.idclient into v_client_id_leased
    from leasing p
    where p.idcontainer = p_containerID
        AND leasingdate = (Select Max(leasingdate)
                        from leasing
                        where idcontainer = p_containerID);

    if v_client <> v_client_id_leased
    then
        raise ex_container_not_leased_by_client;
    end if;



    Select MAX(idcargomanifest) into v_cargo_manifest
    from manifestcontainer
    where idcontainer = p_containerID;

    Select idcargomanifesttype into v_cargo_m_type
    from cargomanifest
    where idcargomanifest = v_cargo_manifest;

    IF v_cargo_m_type = 1
    Then
        Select shipname into v_ship_name
        from Ship
        where idVehicle = (select idvehicle
                           from Vehicle
                           where idvehicle = (Select idVehicle
                                              from Trip
                                              where idTrip = (Select idTrip
                                                              from ArrivalDeparture
                                                              where idCargoManifestLoad = v_cargo_manifest)));

        Select Distinct Concat('SHIP,', v_ship_name) into m_result from ship;
        return (m_result);

    ELSE
        Select name into v_port_name
        from PortWarehouse
        where idPortWarehouse = (select arrivaldeparture.idportwarehousedeparture
                                 from ArrivalDeparture
                                where idCargoManifestUnload = v_cargo_manifest);

        Select Distinct Concat('PORT,', v_port_name) into m_result from ship;
        return (m_result);

    END IF;

EXCEPTION
        WHEN ex_container_not_found then
            raise_application_error(-20010, 'Invalid Container id');
        When ex_container_not_leased then
            raise_application_error(-20009, 'Container is not leased at the moment');
        When ex_container_not_leased_by_client then
            raise_application_error(-20011, 'Container is not leased by client');

END;