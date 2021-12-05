set serveroutput on;
declare
    situation varchar(100);
BEGIN
    situation:= fnc_get_container_status(4);
    dbms_output.put_line(situation);
end;

