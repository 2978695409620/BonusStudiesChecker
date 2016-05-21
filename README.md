# BonusStudiesChecker
An application that checks for new studies from WLU's research studies site

Requirements:
Maven and JDK 8

How To Use:
Setup environment variable for login credentials: userID for username, and password for password

Create mysql database using the following:
create table Studies (id INT NOT NULL AUTO_INCREMENT, 
                      name varchar(255), 
                      PRIMARY KEY(id));
                      
Set database configuration in database.properties

Set mail configuration in application.properties

To run:

mvn clean package

cd target

java -jar scraper-1.0.0.jar
