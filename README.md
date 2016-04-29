# company-xyz
########################################################################

RUN PROJECT IN LOCAL MODE FOLLOW THE STEPS BELLOW.
THE STEPS BELLOW CONSIDER JAVA JDK 7, MYSQL AND MAVEN WAS INSTALLED ON COMPUTER OR SERVER

########################################################################


1 - EXECUTE THE SCRIPT "/create_local_database.sql" ON MYSQL DATABASE
IF YOUR MYSQL USER NOT HAVE THE USER AND PASSWORD WITH VALUE "root" THE STEP BELOW IS NECESSARY

2 - CHANGE THE USER AND PASSWORD ON FILE "/code/company-sales/comp-core/src/main/resources/application-local.properties"
EXEMPLE OF PROPERTIES
	spring.datasource.url = jdbc:mysql://${YOUR_MYSQL_HOST}:${YOUR_MYSQL_PORT}/company_xyz
    spring.datasource.username = ${YOUR_USERANME}
    spring.datasource.password = ${YOU_PASSWORD}


3 - ACCESS COMPUTER TERMINAL 

4 - GO TO THE FOLDER "/Code/company-sales/company-core/"
	cd ${PROJECT_DIR}/Code/company-sales/company-core 

6 -  RUN THE PROJECT WITH THE COMMANDS BELLOW
   	6.1 - mvn clean install
	6.2 - mvn spring-boot:run -Dspring.profiles.active=local
	
Database diagram

![alt tag](https://raw.githubusercontent.com/mlimavieira/company-xyz/master/SQL/diagram.png)



