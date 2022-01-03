INSERT INTO Person VALUES(1, 'João Teixeira', TO_DATE('2002-09-12', 'YYYY-MM-DD'));
INSERT INTO Person VALUES(2, 'Luís Figo', TO_DATE('1990-09-10', 'YYYY-MM-DD'));
INSERT INTO Person VALUES(3, 'João Silva', TO_DATE('2000-08-01', 'YYYY-MM-DD'));
INSERT INTO Person VALUES(4, 'Zé Camionista', TO_DATE('2000-08-01', 'YYYY-MM-DD'));

INSERT INTO SystemUser VALUES(1, 'joaoSilva12', 'joaosilva12@lei.sem3.pt');
INSERT INTO SystemUser VALUES(2, 'joaoTeixeira08', 'joaoteixeira08@lei.sem3.pt');
INSERT INTO SystemUser VALUES(3, 'luisFigo07', 'luisfigo07@lei.sem3.pt');

INSERT INTO SystemUserRole VALUES(1, 1);
INSERT INTO SystemUserRole VALUES(2, 2);
INSERT INTO SystemUserRole VALUES(3, 3);
INSERT INTO SystemUserRole VALUES(5, 4);

INSERT INTO Employee VALUES(1, 1, 2);
INSERT INTO Employee VALUES(2, 2, 3);
INSERT INTO Employee VALUES(3, 4, 5);

INSERT INTO EmployeeOccupation VALUES(1, 1);
INSERT INTO EmployeeOccupation VALUES(2, 2);
INSERT INTO EmployeeOccupation VALUES(3, 3);

INSERT INTO Client VALUES(3, 1);

INSERT INTO Leasing VALUES(3, 'LPRU1628240', TO_TIMESTAMP('2021-11-30 00:00', 'YYYY-MM-DD HH24:MI'));
INSERT INTO Leasing VALUES(3, 'LPRU5977032', TO_TIMESTAMP('2021-11-30 00:00', 'YYYY-MM-DD HH24:MI'));
INSERT INTO Leasing VALUES(3, 'LPRU7664786', TO_TIMESTAMP('2021-12-01 01:00', 'YYYY-MM-DD HH24:MI'));

INSERT INTO Vehicle VALUES(1, 2);
INSERT INTO Truck VALUES(1, 3);

INSERT INTO PortWarehouseConnection VALUES(101, 10136);
INSERT INTO PortWarehouseConnection VALUES(102, 10136);
INSERT INTO PortWarehouseConnection VALUES(103, 10136);
INSERT INTO PortWarehouseConnection VALUES(104, 10136);
INSERT INTO PortWarehouseConnection VALUES(105, 10136);
INSERT INTO PortWarehouseConnection VALUES(106, 10136);

INSERT INTO PortWarehouseConnection VALUES(301, 14113);
INSERT INTO PortWarehouseConnection VALUES(302, 14113);
INSERT INTO PortWarehouseConnection VALUES(303, 14113);
INSERT INTO PortWarehouseConnection VALUES(304, 14113);
INSERT INTO PortWarehouseConnection VALUES(305, 14113);
INSERT INTO PortWarehouseConnection VALUES(306, 14113);
INSERT INTO PortWarehouseConnection VALUES(307, 14113);
INSERT INTO PortWarehouseConnection VALUES(308, 14113);

INSERT INTO PortWarehouseConnection VALUES(401, 10860);
INSERT INTO PortWarehouseConnection VALUES(402, 10860);
INSERT INTO PortWarehouseConnection VALUES(403, 10860);
INSERT INTO PortWarehouseConnection VALUES(404, 10860);

INSERT INTO PortWarehouseStaff VALUES(10860, 1);
