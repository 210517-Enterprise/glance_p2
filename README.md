# Glance

## Overview
Glance is a simple banking app for users to view information, balances and other account data in one central location. 

## Team Members
* Jack Welsh - Project Lead
* Kyle Castillo - Backend Services
* Nse Obot - Backend API and Controllers
* James Butler - Front End and integration

## Technologies Used
* PostgreSQL 42.2.22
* Java 8.0 (1.8)
* Apache Commons 2.5.0
* JUnit 4.13.1
* JbCrypt 0.4
* Amazon Web Services: EC2 + RDS
* HTML 5, Bootstrap, ES6
* Plaid API

## Features
* Simple web-based architecture
* Uses Plaid's Link system to securely and safely link account information
* Ability to link and view accounts from multiple banks in one location

## Coming Soon
* Ability to view details, including most recent transactions for individual accounts
* Ability to add and remove user generated savings and spending goals and assosciate them with specific accounts
* Improved data processing to improve performance at the user end

## Getting Started  
### To run the application locally:
* Clone the repository to your local machine
* Create an application.proprties file within src/main/resources and populate it with appropriate values to connect to a database (EH2 with ddl.auto=create is recommended)
* From a terminal in the Glance directory run ``mvn clean install``
* Then from the same location run ```mvn spring-boot:run```

## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
