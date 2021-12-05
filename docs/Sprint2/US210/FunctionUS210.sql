Create or replace function fnc_get_n_monday_available_ships(p_date DATE ) return sys_refcursor
as
    v_next_monday Date;
    c_available_ships sys_refcursor;
    
BEGIN
     SELECT MAX(NEXT_DAY(TRUNC(p_date), 2)) into v_next_monday
     FROM ship;
     
     Open c_available_ships 
     for
        Select idvehicle, shipname, mmsi, (select position.latitude from position 
                            where idposition = (select idposition from shipposition
                                                where basedatetime = (SELECT Max(basedatetime) from shipposition
                                                                        where idship = idvehicle))) as latitude
                                                                        
                                                                        
                                    ,(select position.longitude from position 
                            where idposition = (select idposition from shipposition
                                                where basedatetime = (SELECT Max(basedatetime) from shipposition
                                                                        where idship = idvehicle))) as longitude
        from ship s
        where idvehicle NOT IN ( select idvehicle from trip
                                where endDate > v_next_monday);
                                
        return (c_available_ships);
end;
    