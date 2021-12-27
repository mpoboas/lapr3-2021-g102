create or replace trigger trg_create_audit_trail 
after Insert or Update or Delete
on manifestcontainer
for each row
DECLARE
    v_operation VARCHAR2(50);
    v_operation_date Timestamp;
    v_idContainer Container.idcontainer%type;
    v_idCargoManifest CargoManifest.idcargomanifest%type;
Begin
    Select sysdate into v_operation_date
    from dual;

    if inserting 
    then
        v_operation := 'INSERT';
        v_idContainer := :new.idcontainer;
        v_idcargomanifest := :new.idcargomanifest;
    end if;

    if updating 
    then 
        v_operation:= 'UPDATE';
        v_idContainer := :old.idcontainer;
        v_idcargomanifest := :old.idcargomanifest;
    end if;

    if deleting 
    then 
        v_operation:= 'DELETE';
        v_idContainer := :old.idcontainer;
        v_idcargomanifest := :old.idcargomanifest;
    end if;

    Insert into audittrail (idaudittrail, idcargomanifest, idcontainer, iduser, datetime, operationtype)
    values (seq_audit_trail.nextval ,v_idCargoManifest,v_idContainer, null, v_operation_date, v_operation);

end;