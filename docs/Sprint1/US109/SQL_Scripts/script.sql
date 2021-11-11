DESCRIBE SHIP;
INSERT INTO SHIP VALUES (112, 1, 'Test ship', 'Call sign',122 ,131 ,12 ,133 ,1331 );
INSERT INTO SHIP VALUES (211, 2, 'Test SHIP 2', 'test',1221 , 121,123 ,121 ,123 );
INSERT INTO SHIP VALUES (3300, 3, 'Test ship error, must be error because this string has more than 100 char', 'sign',232, 131, 13,311 , 242 );--expected error
INSERT INTO SHIP VALUES (412, 4, 'othertest', 'call sign bigger than 15 char',313,1331 ,1313 ,13133 ,1313  );
INSERT INTO SHIP VALUES (112, 5, 'TEST SHIP', 'sign', 1721, 142, 1412, 1421, 141 );--expected error
INSERT INTO SHIP VALUES (null, 4, 'Days of the Phoenix', 'cs',143 ,143 ,1412 ,14 ,141 );--expeted error
INSERT INTO SHIP VALUES (711, 5, null, 'AFI',1412 ,24 ,124 ,1212 , 1241 );
SELECT * FROM SHIP;
