set serveroutput on;
Declare
    c_cursor SYS_REFCURSOR;
    v_date DATE;
    v_idVec INTEGER;
    v_shipN VARCHAR(100);
    v_mmsi INTEGER;
    v_latitude NUMBER;
    v_longitude NUMBER;
    
BEGIN
    SELECT MAX(NEXT_DAY(TRUNC(SYSDATE), 2)) into v_date
    FROM ship;

    dbms_output.put_line('Available ships on: ' || v_date)
           
    c_cursor := fnc_get_n_monday_available_ships(sysdate);
    loop
        fetch c_cursor INTO v_idVEC, v_shipN,v_mmsi,v_latitude,v_longitude;
        Exit when c_cursor%NOTFOUND;
        dbms_output.put_line('Vehicle ID: '||v_idVEC ||' | '
                            || 'Ship Name: ' ||  v_shipN ||' | '
                            || 'Ship MMSI: ' || v_mmsi ||' | '
                            || 'Latitude: ' || v_latitude ||' | '
                            || 'Longitude: '|| v_longitude);
    END LOOP;
    close c_cursor;
end;