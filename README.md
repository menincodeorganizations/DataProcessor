# DataProcessor
A small Java Spring REST application for importing CSV data and allowing user to search from that data.


There are two ways to run this application.

If you have Maven installed and its PATH configured as a system variable, you only need to run one command to start the application (inside the app folder):

***mvn spring-boot:run***

--

If not, you can build the jar and starting it by running these two following commands (also from inside the application root folder):

***./mvnw clean package***

***java -jar target/DataProcessor-0.0.1-SNAPSHOT.jar***
