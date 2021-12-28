select * from manifestcontainer
where idcontainer = 'LPRU5603455';

insert INTO manifestcontainer (idcargomanifest, idcontainer, containerpositionx,containerpositiony,containerpositionz)
VALUES (8,'LPRU5603455',1,1,1);

update manifestcontainer
set containerpositionx = 2
where idcargomanifest = 8 and idcontainer='LPRU5603455';

Delete from manifestcontainer
where idcargomanifest = 8 and idcontainer='LPRU5603455';

set SERVEROUTPUT ON;

Declare
    c_cursor sys_refcursor;
    v_user_id varchar(50);
    v_date_time TIMESTAMP;
    v_type_operation varchar2(50);
    v_container_id VARCHAR2(11);
    v_cargo_manifest_id INTEGER;
BEGIN
    c_cursor := fnc_get_audit_trails('LPRU5603455',8);
    loop
        fetch c_cursor into v_user_id, v_date_time, v_type_operation, v_container_id, v_cargo_manifest_id;
        exit when c_cursor%NOTFOUND;
        dbms_output.put_line('User/Login: '||v_user_id ||' | '
                            || 'DateTime of Operation: ' ||  v_date_time ||' | '
                            || 'Type of Operation: ' || v_type_operation ||' | '
                            || 'Container ID: ' || v_container_id ||' | '
                            || 'Cargo Manifest ID: '||v_cargo_manifest_id);
    end loop;
    close c_cursor;
        
END;