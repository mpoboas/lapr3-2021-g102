set serveroutput on;
Declare
    c_cursor SYS_REFCURSOR;
    v_ship_mmsi integer;
    v_startdate timestamp;
    v_startPlaceId integer;
    v_startPlace varchar(50);
    v_enddate timestamp;
    v_endPlaceId integer;
    v_endPlace varchar(50);
Begin
--    v_ship_mmsi:= 9999299; -- id inv�lido
    v_ship_mmsi:= 316005667; -- ship com viagens
--    v_ship_mmsi:= 255806169; -- ship sem viagens
--    c_cursor:= fnc_get_voyages_below_threshold(v_ship_mmsi,66); -- Usando apenas a função para obter as viagens
    c_cursor:= fnc_get_voyages_below_threshold_with_user_verification('sergioCosta15',v_ship_mmsi,66); -- Usando a função que tbm verifica o user

    DBMS_OUTPUT.PUT_LINE('Voyages with occupancy rate below threshold of ship: '|| v_ship_mmsi);
    loop
        fetch c_cursor INTO v_startdate, v_startplaceid, v_startplace, v_enddate, v_endplaceid, v_endplace;
        Exit when c_cursor%NOTFOUND;
        dbms_output.put_line('Start Date: '|| v_startdate ||' | '
                            || 'Start Place ID: ' ||  v_startplaceid ||' | '
                            || 'Start Place Name: ' || v_startplace ||' | '
                            || 'End Date: ' || v_enddate ||' | '
                            || 'End Place ID: ' ||  v_endplaceid ||' | '
                            || 'End Place Name: ' || v_endplace);
    END LOOP;
    close c_cursor;

end;