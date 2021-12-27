Create or REPLACE FUNCTION fnc_get_container_status(p_containerID container.idcontainer%TYPE) Return varchar 
AS
    v_cargo_manifest cargomanifest.idcargomanifest%TYPE;
    v_cargo_m_type cargomanifest.idcargomanifesttype%type;
    
    v_ship_name ship.shipname%type;
    v_port_name portwarehouse.name%type;
    
    m_result varchar(100);
    ex_container_not_found exception;
    
BEGIN
    Select MAX(idcargomanifest) into v_cargo_manifest
    from manifestcontainer
    where idcontainer = p_containerID;
    
    IF v_cargo_manifest = NULL THEN
       raise ex_container_not_found;
    END IF;
    
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
        where idPortWarehouse = (select idPortWarehouseDeparture
                                from ArrivalDeparture
                                where idCargoManifestUnload = v_cargo_manifest);
        
        Select Distinct Concat('PORT,', v_port_name) into m_result from ship;
        return (m_result);
        
    END IF;

EXCEPTION
        WHEN ex_container_not_found then
            raise_application_error(-20001, 'Non-existent container');

END;
