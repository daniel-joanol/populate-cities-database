# populate-cities-database
An api to populate a database with countries, regions and cities from the free versions of the xlsx files provided by https://simplemaps.com/.

## Setup

To use the API you're going to need to setup two environmental variables: POPULATE_DB_SCHEMA and POPULATE_DB_FILES_PATH. 

The first is the schema, as the name suggests. And the second is the path where the files can be found. Inside resources/docs/ you can find 3 files: es.xlsx, br.xlsx and us.xlsx.

You also need to create the variables POPULATE_DB_DATABASE, POPULATE_DB_USER and POPULATE_DB_PASS.

To populate the database access http://localhost:8080/swagger-ui/index.html#/populate-db-controller/populateDBsUsingPOST.

## Libraries

* Swagger
* Lombok
* PostgreSQL
* SpringJPA
* Apache-POI

## Entities

### Country

* Long id
* String name

### Region

* Long id
* String name
* Long countryId

### City

* Long id
* String name
* Long regionId