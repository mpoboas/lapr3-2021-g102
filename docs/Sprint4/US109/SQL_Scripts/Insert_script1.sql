DESCRIBE SHIP;
DESCRIBE PERSON;
--LOAD DATA
INSERT INTO VEHICLE VALUES (1);--PASS
INSERT INTO VEHICLE VALUES (2);--PASS
INSERT INTO VEHICLE VALUES (3);--PASS
INSERT INTO VEHICLE VALUES (4);--PASS
INSERT INTO VEHICLE VALUES (5);--PASS
INSERT INTO VEHICLE VALUES (6);--PASS
INSERT INTO VEHICLE VALUES (7);--PASS
--END LOAD
INSERT INTO SHIP VALUES (1,112, 1, 'Test ship', 'Call sign',122 ,131 ,12 ,133 ,1331 );--pass
INSERT INTO SHIP VALUES (2,211, 2, 'Test SHIP 2', 'test',1221 , 121,123 ,121 ,123 );--pass
INSERT INTO SHIP VALUES (3,3300, 3, 'Test ship error, must be error because this string has more than 100 char', 'sign',232, 131, 13,311 , 242 );--expected error on SHIPNAME
INSERT INTO SHIP VALUES (4,412, 4, 'othertest', 'call sign bigger than 15 char',313,1331 ,1313 ,13133 ,1313  );-- expected error on callsign
INSERT INTO SHIP VALUES (5,112, 5, 'TEST SHIP', 'sign', 1721, 142, 1412, 1421, 141 );--EXPECTED ERROR
INSERT INTO SHIP VALUES (6,null, 4, 'Days of the Phoenix', 'cs',143 ,143 ,1412 ,14 ,141 );--expeted error on MMSI
INSERT INTO SHIP VALUES (7,711, 5, null, 'AFI',1412 ,24 ,124 ,1212 , 1241 );--pass
INSERT INTO PERSON VALUES (1222,'Person Test', '2000.12.20');--pass
INSERT INTO PERSON VALUES (1232,'Person Test', '2000-12-20');--pass
INSERT INTO PERSON VALUES (21,'Person Test', 2000-12-20);--expacted error because date isn't between single quote
INSERT INTO PERSON VALUES (1222,'Test', '1999.12.20'); -- expected error key already exists
INSERT INTO PERSON VALUES (null,'PT', '1999.12.20'); -- expected error key is null


SELECT * FROM SHIP;

SELECT * FROM PERSON;