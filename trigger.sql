create trigger POSITION_ON_INSERT
    before insert
    on POSITION
    for each row
BEGIN
    Select position_sequence.nextval
  INTO :new.IDPOSITION
  FROM dual;
END;