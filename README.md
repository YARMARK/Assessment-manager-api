# Assessment-Manager-Api

## Table of contents

* [General info](#general-info)
* [Required functionality](#required-functionality)
* [Technologies](#technologies)
* [Run](#run)
* [Run (by Cloud Foundry)](#run by cf)
* [Possible issues](#possible issues)

## General info

Application built on Spring Boot as API-provider to help admin with creating folders and putting
predefined template files for the next assessment date and sending notifications for both sides of
the assessment. All the users are stored in the database.
There is a front-end side like an API-consumer (Google Drive dashboard - custom plugin) for
displaying a list of employees to select one. If you choose an employee, you’ll get the next panel
with folders (2022.10, 2022.11, 2022.12, 2023.01 including current month) . In these folders admin
has the possibility of creating folders with names like the next pattern:  firstName_lastName.
Before creating a folder by following pattern firstName_lastName you might select the scope of
target files to path into this folder (only part or all of them). The pattern for folder names like
YYYY.MM (this is necessary in the context of ordering) or firstName_lastName can be changed.
This application has integration with Google Drive (folders and files), JIRA (Employees info like
first and last name).
To update data about current employees into a database we need to create scheduled jobs to get all
users from JIRA and compare them with employers from the application's database. This job should be
running each day at 6 pm.
To create a folder for next month we should create a scheduled job to do it. This job should be
running on the last day at 7 am of each month.
To check users who need to be added for assessment on the current day we should create scheduled
job. This job should be running each day at 7 a.m. This job should find all the users with last
assessment date like (current_date minus 365 days) and set a true boolean field to mark them as
needs to be added for assessment. By following this marker we can find all the users and order
them by date to get an assessment queue to display them for admin. After assessment, we must change
the boolean field to false and update last_assessment_date.

## Required functionality

####              * API operations for employees info (get all, get by id, filter by period, get list of users) (getAll - without ordering )

####              * Exception handler

####              * A scheduled job for checking new users from JIRA. New users should be stored in the application's database.

####              * A scheduled job for creating a new folder for next month by following the name patterns.

####              * A scheduled job to mark employees as targets for assessments.

## Technologies

* Java - 11
* Security - Basic Authorization.
* Database - PostgreSql(prod, test env) and H2(dev env)
* DB migrations - Liquibase
* ORM: Hibernate
* Spring Data JPA
* API Documentation: Open API (former Swagger)
* Checkstyle/Formatter: Google formatter (https://github.com/google/google-java-format)
* Deploy - Docker, CloudFoundry
* Task scheduling - Spring Scheduler
* Tests - Mockito, JUnit5
* VCS - Git

## Run

To run this project locally by using browser:

```
Create settings for tomcat 8.5.75:
- http://localhost:8085/assessment-manager-api/
- -Dspring.profiles.active=dev
```

```
Link : localhost:8085/

username : sa
password : 
```

To send any request by postman:

```

```

## Run (by Cloud Foundry)

To run this project by using cf cloud use next step:

```
Create services with name like services ref in manifest.yml file:

    cf create service <Service name><Service plan><Instance name>
```

```
Push application by using next CLI command:

    cf push -p target/Assessment-manager-api-0.0.1-SNAPSHOT.jar
```

```
Tap the link and use it:


```

To send any request by postman:

```

```

## Possible issues

* Before the deployment PostgerSQL instance took place on work space in SAP BTP account. This
  instance was used by another liquibase application.Several bugs appeared after deployment an
  Assessment-Manager-Api application and attempt to bind PsotgreSQL instance with
  Assessment-Manager-Api. These bugs were tied with liquibase bean, which had to be created manually
  and with PostgreSQL instance. New instance of PostgreSQL was created to fix this bug. It needs to
  use a new instance of PostgreSQL with new application to avoid this problem.