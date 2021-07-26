# Kafka in Spring Boot 
A Kafka Producer and Consumer implementation coded in Spring Boot. The Producer project uses REST API's and a JSON serializer to add to a Kafka Topic and the Consumer project pulls the data from the Kafka Topic and uses a JSON deserializer to examine its properties. This project was created to learn more about Spring Boot, Rest API's, and Kafka.


**Features**

 - Kafka Consumer uses retry policies to handle timeouts
 - Kafka consumer uses error-handling to skip items that can not be deserialized
 - Spring Boot project can be easily containerized 


**Assumptions:**

 - Kafka and Zookeeper is installed an running on host machine
 - Kafka Topic is created and can be accessed by Spring Boot program 
 - Kafka port and Kafka topic name is configured in code. 


## How to run 

1. Download the repository to your local machine with the following command:

   `git clone https://github.com/eshaanm25/springboot-kafka`
4. Use IntelliJ, Eclipse, or other IDE to install Maven dependencies and run the program
5. Use Postman or any other API tool to make a JSON formatted POST request to the server hosted by Spring Boot. 

