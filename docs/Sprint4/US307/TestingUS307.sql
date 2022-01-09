-- Should raise warning because it exceeds the warehouse's available capacity
INSERT INTO ARRIVALDEPARTURE VALUES(4,501,502,'01/10/2022 00:45:00','01/10/2022 01:00:00','01/10/2022 00:55:00',null,31);
-- Shouldn't raise warning
INSERT INTO ARRIVALDEPARTURE VALUES(4,501,503,'01/10/2022 00:45:00','01/10/2022 01:00:00','01/10/2022 00:55:00',null,31);