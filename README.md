# spring-boot-app
A spring-boot application with a REST controller, which expose a Swagger API catalog.

The application has the following APIs:


* List of the inventory items list (item no, name, amount, inventory code)
* Read item details (by item no)
* Withdrawal quantity of a specific item from stock
* Deposit quantity of a specific item to stock
* Add item to stock
* Delete an item from stock  
* Data should be persisted on H2 DB using JPA.
