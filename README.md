# BonusStudiesChecker
An application that checks for new studies from WLU's research studies site https://wlu-ls.sona-systems.com/

How To Use:
Setup environment variable for login credentials: userID for username, and password for password

Create mysql database using the following:
create table Studies (id INT NOT NULL AUTO_INCREMENT, 
                      name varchar(255), 
                      PRIMARY KEY(id));
                      
Set database configuration in database.properties
