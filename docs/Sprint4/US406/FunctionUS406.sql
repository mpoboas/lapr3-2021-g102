CREATE OR REPLACE VIEW VOYAGES AS
    Select idtrip, idvehicle,
       (Select min(departuredate) from arrivaldeparture where idtrip = trip.idtrip) startDate,
       idportwarehousedeparture idPortStart,
       (select name from portwarehouse where idportwarehouse = trip.idportwarehousedeparture) namePlaceOrig,
       idportwarehousearrival idPortDest,
       (select name from portwarehouse where idportwarehouse = trip.idportwarehousearrival) namePlaceDest,
       enddate endDate
    from trip;

create or replace function fnc_get_voyages_below_threshold(p_ship_mmsi ship.mmsi%type, p_threshold number) return SYS_REFCURSOR
as
    c_voyages_below sys_refcursor;
    v_count integer;

    ex_ship_not_found exception;
    ex_no_voyages exception;


Begin
    Select count(idvehicle) into v_count
    from ship 
    where ship.mmsi = p_ship_mmsi;

    if v_count = 0
    then
        raise ex_ship_not_found;
    end if;

    Select count(idtrip) into  v_count
    from trip 
    where idvehicle = (select idvehicle 
                        from ship 
                        where mmsi = p_ship_mmsi);

    if v_count = 0
    then
        raise ex_no_voyages;
    end if;

    Open c_voyages_below 
    for
        select startdate, idportstart, nameplaceorig, enddate, idportdest, nameplacedest
        from voyages
        where enddate < sysdate 
            and idvehicle = (select idvehicle 
                            from ship 
                            where mmsi = p_ship_mmsi)
            and getoccupancyratepercminperiod(p_ship_mmsi,startdate,enddate) < p_threshold;

    return (c_voyages_below);

    Exception
        when ex_ship_not_found then
            raise_application_error(-20001, 'Invalid Ship id');
        when ex_no_voyages then
            raise_application_error(-20002, 'The ship with id: ' || p_ship_mmsi || ' has not registered voyages');

End;