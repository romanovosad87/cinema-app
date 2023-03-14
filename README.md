# #cinema-app
#### `A simple simulator of cinema service`

**Description**

Web-app for reservation tickets, supports registration, authentication and CRUD operations.

**Project structure:**
1. DAO - CRUD operations.
2. Service - business logic.
3. Controllers - handle requests, call services and send responses.

**Features:**
- register and login as a user
- create and find movies
- create and find cinema-halls
- create, update, delete and find available movie sessions
- add tickets to shopping cart
- complete an order

**Technologies:**
- Java 17
- Tomcat 10.0.27
- Spring 6 (WEB, Security)
- Hibernate 6
- EHCache 3
- Liquibase
- Log4j2
- Thymeleaf 3
- JUnit 5
- Mockito
- HSQLDB

**How to run application:**
1. Clone the project to your IDE from GitHub.
2. Configure Tomcat 10.хх (set "/" in deployment - cinema_app_war_exploded).
3. Configure **[db.properties](https://github.com/romanovosad87/cinema-app/blob/main/src/main/resources/db.properties)** file with your URL, USERNAME, PASSWORD, DRIVER.
4. Configure **[log4j2.properties](https://github.com/romanovosad87/cinema-app/blob/main/src/main/resources/log4j2.properties)** file with your basePath to this project.
5. Configure **[liquibase.properties](https://github.com/romanovosad87/cinema-app/blob/main/src/main/resources/liquibase.properties)** file with your URL, USERNAME, PASSWORD, DRIVER.
6. Configure **[create-all-tables.sql](https://github.com/romanovosad87/cinema-app/blob/main/src/main/resources/db.changelog/changes/create-all-tables.sql)** file with your NAME.
7. Run command mvn clean package in terminal.
8. Click debug configuration.

**You can test the app by this** 
**[link](http://http://cinema-env.eba-cpqze2bi.eu-west-3.elasticbeanstalk.com)**

(used [Amazon Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk/?nc1=h_ls) and [Amazon RDS](https://aws.amazon.com/rds/?p=ft&c=db&z=3))
