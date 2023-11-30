# AccountApi

AccountApi is a service for managing bank accounts, enabling transactions and maintaining a transaction history.

### Features Implemented::

Account Creation and Management:
Accounts are created by specifying the recipient's name and a four-digit PIN code.
Automatic generation of account numbers.
Multiple accounts can be created for a single recipient.

Transaction Operations:
Deposit, withdrawal, and fund transfer functionalities between accounts.
PIN verification for operations involving fund deduction.

Error Handling:
Appropriate error codes are returned for unsuccessful operations.

Data Representation:
JSON data format used for requests and responses.

Testing:
Unit tests were implemented to validate the functionality of the service layer.

### Technology Stack:

* Java
* Spring boot
* Lombok
* Maven
* Swagger
* JUnit

### Running the Application:

Clone the repository.
Run mvn spring-boot:run from the terminal to start the application.
After the application starts, you can explore the controller endpoints, request schema, specification, and test the
functionality by navigating to the link http://localhost:8080/swagger-ui.html in your browser.
Stop the application by pressing Ctrl + C.




