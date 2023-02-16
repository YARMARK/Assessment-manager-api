# [Assessment-Manager-Api]()

## Table of contents

* [Description](#description)
* [Required functionality](#required-functionality)
* [Technologies](#technologies)
* [Installation](#installation)
* [Run](#run)
* [Run (by Cloud Foundry)](#run-by-cloud-foundry)
* [Issue](#issue)

## Description

Application built on Spring Boot as API-provider to help admin with creating folders and putting
predefined template files for the next assessment date and sending notifications for both sides of
the assessment. This application has integration with Google Drive (folders and files), JIRA (
Employees info like first and last name).

## Required functionality

* API operations for employees info (get all, get by id, filter by period, get list of users) (
  getAll - without ordering )
* Exception handler
* A scheduled job for checking new users from JIRA. New users should be stored in the application's
  database.
* A scheduled job for creating a new folder for next month by following the name patterns.
* A scheduled job to mark employees as targets for assessments.

## Technologies

* Java - 11
* Security - Basic Authorization.
* Database - PostgreSQL(prod, test env) and H2(dev env)
* DB migrations - Liquibase
* ORM: Hibernate
* Spring Data JPA
* API Documentation: Open API (former Swagger)
* Checkstyle/Formatter: Google formatter (https://github.com/google/google-java-format)
* Deploy - Docker, CloudFoundry(SAP BTP)
* Task scheduling - Spring Scheduler
* Tests - Mockito, JUnit5
* VCS - Git
* Building tool - Maven
* IDE - IntelliJ IDEA

## Installation
* 1-st step - Clone the git repo

```
https://github.com/YARMARK/Assessment-manager-api.git
```

* 2-d step - Open project folder in IDE
* 3-d step - Explore

## Run

To run this project locally by using browser:

* 1-st step - Run AssessmentManagerApi.java in Intellij IDEA
* 2-d step - Open link in any web browser and login in database

```
Link : http://localhost:8085/h2-console

username : sa
password : 
```

To send any request by postman:

* Get employee from database by ID

```
http://localhost:8085/assessment-manager-api/employees/{ID}
```

* Get all employees

```
http://localhost:8085/assessment-manager-api/employees
```

## Run (by Cloud Foundry)

To run this project by using cf cloud use next step:

* Create services with name like services ref in manifest.yml file

```
    cf create service <Service name><Service plan><Instance name>
```

* Push application by using next CLI command

```
    cf push -p target/Assessment-manager-api-0.0.1-SNAPSHOT.jar
```

* Tap the link and use it


## Issue

* Before the deployment PostgerSQL instance took place on work space in SAP BTP account. This
  instance was used by another liquibase application.Several bugs appeared after deployment an
  Assessment-Manager-Api application and attempt to bind PsotgreSQL instance with
  Assessment-Manager-Api. These bugs were tied with liquibase bean, which had to be created manually
  and with PostgreSQL instance. New instance of PostgreSQL was created to fix this bug. It needs to
  use a new instance of PostgreSQL with new application to avoid this problem.