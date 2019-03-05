# COURSEWORK FOR ENTERPRISE MIDDLEWARE
### REPORT
Tong Zhou 180275186

## 1.	DESCRIPTION FOR STSTEM STRUCTURE
 
The project is a Java EE application with RESTful architect, which expose the API to outer system to quote. With the help of Hibernate Persistence Layer, data structure can be easily managed through Hibernate annotations.
 
Take Booking as example, Rest Service is the point communicate with outer system, which is annotated with RESTful annotation and Swagger annotation to describe each API and their purpose, parameter and returning.
Then BookingRestService will call Booking service to do detail operation, like validation and complicated operations.
Booking Repository work as the persistence layer which cope with the operation related to database, operate the database operations.
The Booking class is the Entity for a Booking, and it also describe one-to-one or one-to-many  relation between the keys and persist it to database with the help of Hibernate.
 
## 2.	TRANSACTIONS AND JTA
Transaction separates our operations in to some atom blocks, which means operations within the block should be either both effective, or none of the operation is effective, this is especially useful when some operations has dependencies with each other, and Bean-managed Transaction can reduce our codes on rolling back for each part of the atom operation.
JTA provides distributed transaction management, which means transaction can be handled and effective through services from different server, which will help us on the transaction management on Part3 tasks while communicating with different servers.

## 3.	PERSISTENCE AND JPA
With the JPA annotation, you no longer need to build a complex data structure in database, because JPA will construct the database automatically. What’s more, you can use the annotation to define one-to-one or one-to-many relations easily, and the Entities are just like a normal Data Entity that can easily store an object to your entity without any other configuration.
## 4.	PROBLEMS DURING DEVELOPING
Since our coursework didn’t strictly requires the standard input and output of the APIs, people students have different understanding through the requirement, which means when we try to quote other’s system, the JSON required by the API may varies a lot from person to person, and sometime other’s definition may not be reasonable. So I have no choice but create DTO for every system even it represent to same object, like Booking object in different system. So, It’s very important to standardize the input and output of the API’s before we even start to work on it.


See [Project Specification](https://github.com/DnsZhou/Individual-Project-for-Enterprise-Middleware/blob/master/ProjectSpecification.md) and [Original Requirement](https://github.com/DnsZhou/enterprise-middleware-coursework) for the requirement of the task
