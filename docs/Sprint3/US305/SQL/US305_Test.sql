-- Test US305 - Get given client´s container route
SET SERVEROUTPUT ON;
DECLARE
v_id_port_warehouse_departure INTEGER;
v_id_port_warehouse_arrival INTEGER;
v_departure_name VARCHAR(100);
v_arrival_name VARCHAR(100);
v_departure_date TIMESTAMP;
v_arrival_date TIMESTAMP;
v_estimated_arrival_date TIMESTAMP;
v_mean_of_transport VARCHAR(50);
p_username_client VARCHAR(50);
p_id_container VARCHAR(11);
c_route SYS_REFCURSOR;
BEGIN
    p_username_client := 'joaoteixeira08';
    p_id_container := 'LPRU1628240';
    
    dbms_output.put_line('Client username: ' || p_username_client);
    dbms_output.put_line('Container ID: ' || p_id_container || chr(10));
    
    c_route := fnc_get_client_container_route(p_username_client, p_id_container);
    LOOP
        FETCH c_route INTO v_id_port_warehouse_departure, v_id_port_warehouse_arrival, v_departure_name, v_arrival_name, v_departure_date, v_arrival_date, v_estimated_arrival_date, v_mean_of_transport;
        EXIT WHEN c_route%NOTFOUND;
        
        IF v_arrival_date = NULL THEN
            DBMS_OUTPUT.PUT_LINE('Container is on board of a ' || v_mean_of_transport
            || ' in the middle of the ocean. The expected date and time of arrival at the port ' || v_id_port_warehouse_arrival 
            || ' is ' || v_estimated_arrival_date);
        ELSIF v_id_port_warehouse_departure = v_id_port_warehouse_arrival THEN
            DBMS_OUTPUT.PUT_LINE('Container was shipped in the PortWarehouse ' || v_departure_name || ' at ' || v_departure_date);
        ELSE
            DBMS_OUTPUT.PUT_LINE('Departure PortWarehouse ID: '|| v_id_port_warehouse_departure ||' | '
                            || 'Arrival PortWarehouse ID: ' ||  v_id_port_warehouse_arrival ||' | '
                            || 'Date and time of departure: ' || v_departure_date ||' | '
                            || 'Date and time of arrival: ' || v_arrival_date ||' | '
                            || 'Date and time of expected arrival: '|| v_estimated_arrival_date ||' | '
                            || 'Mean of transport: ' || v_mean_of_transport);
        END IF;
    END LOOP;
    CLOSE c_route;
END;