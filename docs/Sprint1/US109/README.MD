#US109 - Script to test database integrety

*User story 109 requires the creation of a script to test databse restrictions*
*These these tests were carried with a empty database and should be done so*
Since if all tables were tested by only one script, would lead to confusion, as the file would be big, the script was divided by 5 smaller ones, each one testing a certain group of tables and its restrictions.
They were divided like this:
* Insert_script1: Tables Ship and Person.
* Insert_script2: Tables CARGOMANIFEST, CARGOMANIFESTTYPE, TEAM, PORTWAREHOUSE and POSITION
* Insert_script3: Tables TRUCK, EMPLOYEE and ROLE
* Insert_script4: Tables CERTIFICATE AND CONTAINERCERTIFICATE
* Insert_script5: Tables PORTWAREHOUSESTAFF SHIPPOSITION TRIPULATION ARRIVALDEPARTURE, USERROLE, SYSTEMUSER, PORT, WAREHOUSE and VESSELTYPE.