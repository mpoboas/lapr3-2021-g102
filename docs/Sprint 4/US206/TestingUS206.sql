SET serveroutput ON;
Declare
c_cursor SYS_REFCURSOR;
    v_idCargoManifest INTEGER;
    v_idContainer INTEGER;
    v_containerPosX INTEGER;
    v_containerPosY INTEGER;
    v_containerPosZ INTEGER;
BEGIN

    c_cursor := FNC_GET_CONTAINERS_TO_BE_LOADED(1, SYSDATE);
    loop
fetch c_cursor INTO v_idCargoManifest, v_idContainer, v_containerPosX, v_containerPosY, v_containerPosZ;
        Exit when c_cursor%NOTFOUND;
        dbms_output.put_line('Cargo Manifest ID: '||v_idCargoManifest ||' | '
            || 'Container ID: ' ||  v_idContainer ||' | '
            || 'Container Position (X,Y,Z): ' || v_containerPosX ||' | '|| v_containerPosY ||' | '|| v_containerPosZ);
END LOOP;
close c_cursor;
end;