simulated Car Rental system using object-oriented principles



Requirements

• The system should allow reservation of a car of a given type at a desired date and time for a given

number of days.

• There are 3 types of cars (Sedan, SUV and van).

• The number of cars of each type is limited.

• Use unit tests to prove the system satisfies the requirements.



There is two service class 



1\. CarInventoryService

* &nbsp;  It's loading the Inventory details from application property -- Using Constructor 
* &nbsp;  Checking the car type if it is available we can reserve it -- reserve(String carType)
* &nbsp;  To list the inventory details -- showInventory()

2\. CarReservationService

* To List the Reserved car -- showReservations()
* Validation check (days < 0 || startDate.isBefore(ZonedDateTime.now()) || typeOfCar.isBlank()) then book the car
* If the car is already booked then show the next available date to reserve the car
