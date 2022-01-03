INSERT INTO VehicleType VALUES(1, 'Ship');
INSERT INTO VehicleType VALUES(2, 'Truck');

INSERT INTO Continent VALUES('EU', 'Europe');
INSERT INTO Continent VALUES('AM', 'America');
INSERT INTO Continent VALUES('AF', 'Africa');
INSERT INTO Continent VALUES('AS', 'Asia');
INSERT INTO Continent VALUES('OC', 'Oceania');

INSERT INTO PortWarehouseType VALUES('P', 'Port');
INSERT INTO PortWarehouseType VALUES('W', 'Warehouse');

INSERT INTO ContainerType VALUES(1, 'Refrigerated 7º');
INSERT INTO ContainerType VALUES(2, 'Refrigerated -5º');
INSERT INTO ContainerType VALUES(3, 'Non-refrigerated');

INSERT INTO CargoManifestType VALUES(1, 'Load');
INSERT INTO CargoManifestType VALUES(2, 'Unload');
INSERT INTO CargoManifest VALUES(0, null, null, null);

INSERT INTO Occupation VALUES(1, 'Port Manager', 1500);
INSERT INTO Occupation VALUES(2, 'Warehouse Manager', 1500);
INSERT INTO Occupation VALUES(3, 'Truck Driver', 1500);

INSERT INTO SystemRole VALUES(1, 'Client');
INSERT INTO SystemRole VALUES(2, 'Port Manager');
INSERT INTO SystemRole VALUES(3, 'Warehouse Manager');
INSERT INTO SystemRole VALUES(4, 'Truck Driver');