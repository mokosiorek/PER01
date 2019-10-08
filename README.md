# PROJECT PER01


This project was created with IntelliJ IDEA IDE. And can be run via this IDE.

To run this project you need to install MySQL database and create database PER01. The username and the password are "root". You can change them in persistence.xml in resources folder.

The idea of this project is to simulate system of shops, where customer can place orders of products.

The project consists of following layers:

Model - JPA/Hibernate based model of data which are saved to database. The fetch type in relations is set to lazy for educational purposes, as in web application fetchtype eager should rather be used.

DTO - the layer of separation of hibernate managed entities and classes used in program.

Repository (DAO) - the layer responsible for saving entities into database and querying database with JPQL. It is partially generic.

Validators - the layer, which validates entities when they are saved to database

Service - service layer, which is responsible for business logic and implements methods that are used to extract information from database. The menu service class is responsible for simple UI to provide CRUD operations.

Exceptions - the class of exception, which occurs if there is any error during runtime of program. Information about date and exception message is saved to database in error table.

Json Converters - the data is often displayed in JSON format, this layer implements transformation from object to json format of entities in model. Additionally there is another set of classes for lists of objects, which is used in data initialization.

The json files in main directory (categories.json, countries.json, trades.json) are used to initialize database with data that don't require id values of other entities.






