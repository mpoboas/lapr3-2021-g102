-- Test US306 - Gets the occupancy rate and an estimate of the containers leaving the warehouse during the next 30 days of every warehouse that a port manager has access to.
SET SERVEROUTPUT ON;
DECLARE
    p_port_manager_username VARCHAR(50);
    v_id_warehouse INTEGER;
    v_warehouse_occupancy_rate NUMBER;
    v_warehouse_name VARCHAR(100);
    v_id_container VARCHAR(11);
    v_container_gross_weight NUMBER;
    v_container_type VARCHAR(100);
    c_port_manager_warehouses SYS_REFCURSOR;
    c_containers_leaving_next_30_days SYS_REFCURSOR;
    c_each_warehouse_occupancy_rate SYS_REFCURSOR;
BEGIN
    p_port_manager_username := 'joaosilva12';
        
    c_each_warehouse_occupancy_rate := fnc_get_each_warehouse_occupancy_rate(p_port_manager_username);
    LOOP
    FETCH c_each_warehouse_occupancy_rate INTO v_id_warehouse, v_warehouse_name, v_warehouse_occupancy_rate;
    EXIT WHEN c_each_warehouse_occupancy_rate%NOTFOUND;
    
        DBMS_OUTPUT.PUT_LINE(chr(10) || 'Warehouse ID: ' || v_id_warehouse || ' | '
                        || 'Warehouse: ' || v_warehouse_name || ' | '
                        || 'Warehouse occupancy rate: ' ||  v_warehouse_occupancy_rate || '%');
    
        c_containers_leaving_next_30_days := fnc_get_containers_leaving_next_30_days(v_id_warehouse);
        LOOP
        FETCH c_containers_leaving_next_30_days INTO v_id_container, v_container_gross_weight, v_container_type;
        IF c_containers_leaving_next_30_days%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('No containers are leaving ' || v_warehouse_name || ' in the next 30 days.');
        END IF;
        EXIT WHEN c_containers_leaving_next_30_days%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('Container ID: ' || v_id_container || ' | '
                            || 'Container gross weight: ' || v_container_gross_weight || ' | '
                            || 'Container type: ' || v_container_type);                                
        END LOOP;
        CLOSE c_containers_leaving_next_30_days;           
    END LOOP;
    CLOSE c_each_warehouse_occupancy_rate;
END;