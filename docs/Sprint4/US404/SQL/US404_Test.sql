-- Test US404 - Get the number of days each ship has been idle since the beginning of the current year
SET SERVEROUTPUT ON;
DECLARE
c_number_of_idle_days SYS_REFCURSOR;
v_fleet_manager_username VARCHAR(100);
v_year INTEGER;
v_id_ship INTEGER;
v_ship_name VARCHAR(100);
v_idle_days NUMBER(10,3);
BEGIN
    v_fleet_manager_username := 'sergioCosta15';
    v_year := 2022;
    
    c_number_of_idle_days := fnc_get_each_ship_number_of_idle_days(v_fleet_manager_username, v_year);
    LOOP
        FETCH c_number_of_idle_days INTO v_id_ship, v_ship_name, v_idle_days;
        EXIT WHEN c_number_of_idle_days%NOTFOUND;
            IF v_idle_days IS NULL OR v_idle_days = 0 THEN
                DBMS_OUTPUT.PUT_LINE('Ship ID: ' || v_id_ship || ' | '
                            || 'Ship name: ' || v_ship_name || ' | '
                            || 'Ship does not have idle time.');
            ELSE
                DBMS_OUTPUT.PUT_LINE('Ship ID: ' || v_id_ship || ' | '
                        || 'Ship name: ' || v_ship_name || ' | '
                        || 'Number of idle days since the beginning of the year: ' ||  v_idle_days);
            END IF;
    END LOOP;
    CLOSE c_number_of_idle_days;
END;