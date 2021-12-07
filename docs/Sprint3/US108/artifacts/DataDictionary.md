# Data Dictionary

**Terms, Names or Designations that may be unknown are presented alphabetically.**

| **_Database Element_** | **_Entity_** | **_Caption_** | **_Description_**                                           |                                       
|:------------------------|:-----------------|:-------------|:------------------------------------------------------|
| **cog** | **ShipPosition** | **Course Over Ground** | Represents the Course Over Ground of a ship in a certain position in the map. It is the actual heading of the boat, taking into account some factors, such as the wind effect on the vessel. |
| **sog** | **ShipPosition** | **Speed Over Ground** | Represents the Speed Over Ground of a ship in a certain position in the map. It is the actual Speed of the vessel, comparable to miles/km per hour in a car.|
| **transceiverClass** | **ShipPosition** | **Speed Over Ground** | Represents the ship´s transceiver class in a certain position in the map. This transceiver allows base stations to track the ships´ positioning.|
| **MMSI** | **Ship** | **Maritime Mobile Service Identity** | Represents one of the ship´s unique identification code. |
| **IMO** | **Ship** | **International Maritime Organization** | Represents one of the ship´s unique identification code. |
| **callSign** | **Ship** | **Call Sign** | Represents one of the ship´s unique identification code. |
| **draft** | **Ship** | **Draft** | Represents the vertical distance between the waterline and the bottom of the ship´s hull, in meters.|
| **isoCode** | **Container** | **International Organization for Standardization Code** | Represents the container´s unique identification code.|
|  | **CargoManifest** | **Cargo Manifest** | Represents a manifest that is elaborated by the port staff when a ship loads/unloads cargo in a port/warehouse, which contains information about each container.|
| **totalMovNumber** | **Summary** | **Total number of movements** | Represents the total number of movements made by a ship in a trip.|
| **deltaDistance** | **Summary** | **Delta Distance** | Represents the distance between the last and first position of a ship in a trip.|