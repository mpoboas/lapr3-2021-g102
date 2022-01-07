create FUNCTION getOccupancyRatePerCMInPeriod(idShip integer, date1 varchar, date2 varchar) return number
as
ShipCapacity INTEGER;
average integer;
rate Number;
BEGIN
    Select capacity into ShipCapacity from ship where mmsi = idShip;
    SELECT AVG(c)  INTO average
    FROM (SELECT IDCARGOMANIFEST, COUNT(*) AS c
    FROM MANIFESTCONTAINER WHERE IDCARGOMANIFEST IN (SELECT IDCARGOMANIFEST
    FROM CARGOMANIFEST WHERE ELABORATIONDATE > to_timestamp(date1) and ELABORATIONDATE < to_timestamp(date2) AND (IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTLOAD
    FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM trip WHERE idVehicle in (select idVehicle from ship where mmsi = idShip ))) OR IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTUNLOAD
    FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM trip WHERE idVehicle in (select idVehicle from ship where mmsi = idShip ))))) GROUP BY IDCARGOMANIFEST);
    rate := (average / ShipCapacity) *100;
    return rate;
END;