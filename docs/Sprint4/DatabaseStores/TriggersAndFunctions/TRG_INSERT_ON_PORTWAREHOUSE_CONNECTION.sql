CREATE OR REPLACE TRIGGER trg_on_insert_portwarehouse_connection
BEFORE INSERT ON PortWarehouseConnection
FOR EACH ROW

    DECLARE
        warehouse_type PortWarehouseType.idPortWarehouseType%type;
        port_type PortWarehouseType.idPortWarehouseType%type;
        ex_erro EXCEPTION;
    BEGIN
    
        SELECT PortWarehouse.idPortWarehouseType INTO warehouse_type
        FROM PortWarehouse
        WHERE PortWarehouse.idPortWarehouse = :new.idWarehouse;
        
        SELECT PortWarehouse.idPortWarehouseType INTO port_type
        FROM PortWarehouse
        WHERE PortWarehouse.idPortWarehouse = :new.idPort;
    
        IF(warehouse_type != 'W') THEN
         RAISE ex_erro;
        END IF;
        
        IF(port_type != 'P') THEN
         RAISE ex_erro;
        END IF;
        
        EXCEPTION
        WHEN ex_erro THEN
            raise_application_error(-20001, 'Be sure to type the portwarehouse type correctly.');
END trg_on_insert_portwarehouse_connection;