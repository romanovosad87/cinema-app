# #cinema-app
#### `A simple simulator of cinema service`

## Description

Web-app for tickets reservation, supports registration, authentication, CRUD operations and is based on Hibernate and Spring frameworks.


## Functionality
![82](https://user-images.githubusercontent.com/114337016/225303225-29f59f96-f756-4d19-b7ee-1e28df2c3db2.png)


## Structure

***java/***

- `config` - config classes required by Spring & Hibernate
- `controller` - client-server interaction
- `dao` - communication with the database
- `dto` - dtos that are used for http requests and responses
- `exception` - custom exceptions
- `lib` - custom validators for email, password and confirm password
- `model` - model classes for entities
- `service` - classes that are responsible for business logic
- `service/mapper` - mappers that are used to parse dto to entity and vice versa
- `util` - util class containing date date-time pattern

***resources/***

- `application.properties` - database and Hibernate properties
- `liquibase.properties` - properties for database change managment tool
- `log4j2.properties` - properties for logger
- `db.changelog` - contains an ordered list of chahges to database that needs to be deployed
- `webapp` - contains html views for login, register and index 

***other***

- `pom.xml` - contains maven build configs and dependencies


## Features
- register and login as a user
- create and find movies
- create and find cinema-halls
- create, update, delete and find available movie sessions
- add tickets to shopping cart
- complete an order

## Technologies

Java 17, Tomcat 10.0.27, Spring 6 WEB MVC, Spring 6 Security, Hibernate 6, MySQL, EHCache 3, Liquibase, Log4j2, Thymeleaf 3, JUnit 5, Mockito, HSQLDB.

**You can test the app by this** 
**[link](http://cinema-env.eba-cpqze2bi.eu-west-3.elasticbeanstalk.com)**

(used [Amazon Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk/?nc1=h_ls) and [Amazon RDS](https://aws.amazon.com/rds/?p=ft&c=db&z=3))

To log in as admin use email: admin@i.ua, password: admin123. To log in as user use email: user@i.ua, password: user5678 or you can create a new user on registration page.
To test this app you can use postman. Here is **[link](https://www.postman.com/roman8729/workspace/cinema-app/collection/25812862-e7ee070f-125a-4413-85f8-90777ef3cc90?ctx=documentation)**
to public collection of http requests that you can run.

## How to run an application
1. Clone the project to your IDE from GitHub.
2. Configure Tomcat 10.хх (set "/" in deployment - cinema_app_war_exploded).
3. Configure **[db.properties](https://github.com/romanovosad87/cinema-app/blob/main/src/main/resources/db.properties)** file with your URL, USERNAME, PASSWORD, DRIVER.
4. Configure **[log4j2.properties](https://github.com/romanovosad87/cinema-app/blob/main/src/main/resources/log4j2.properties)** file with your basePath to this project.
5. Configure **[liquibase.properties](https://github.com/romanovosad87/cinema-app/blob/main/src/main/resources/liquibase.properties)** file with your URL, USERNAME, PASSWORD, DRIVER.
6. Configure **[create-all-tables.sql](https://github.com/romanovosad87/cinema-app/blob/main/src/main/resources/db.changelog/changes/create-all-tables.sql)** file with your NAME.
7. Run command mvn clean package in terminal.
8. Click debug configuration.

![image](https://user-images.githubusercontent.com/114337016/225283541-ba72a734-403d-4d77-aa67-953e8c30acb4.png)

