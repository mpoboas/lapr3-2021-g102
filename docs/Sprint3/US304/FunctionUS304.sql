create or replace function fnc_get_audit_trails
    (p_containerID container.idcontainer%type, p_manifestID cargomanifest.idcargomanifest%TYPE) RETURN SYS_REFCURSOR
as
    c_audit_trails sys_refcursor;
Begin
    Open c_audit_trails
    for
        Select (Select username from systemuser where systemuser.iduser = audittrail.iduser), datetime, operationtype, idcontainer, idcargomanifest
        from audittrail
        where idcontainer = p_containerID AND idcargomanifest = p_manifestID
        order by datetime;
        
    return (c_audit_trails);

End;