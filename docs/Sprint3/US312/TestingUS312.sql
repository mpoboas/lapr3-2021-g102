set SERVEROUT on;
---- Execução sem erros da função
declare
situation varchar(100);
BEGIN
    situation:= fnc_get_container_status('LPRU1628240','joaoteixeira08');
    dbms_output.put_line(situation);
end;
---- Container id inválido
declare
situation varchar(100);
BEGIN
    situation:= fnc_get_container_status('LPRU1628242','joaoteixeira08');
    dbms_output.put_line(situation);
end;
---- Container não alugado no momento
declare
situation varchar(100);
BEGIN
    situation:= fnc_get_container_status('LPRU9014362','joaoteixeira08');
    dbms_output.put_line(situation);
end;
---- Container não alugado por cliente
declare
situation varchar(100);
BEGIN
    situation:= fnc_get_container_status('LPRU1628240','luisFigo07');
    dbms_output.put_line(situation);
end;

