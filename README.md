# ddd-example-f1-domain
An example of a model for F1 racing following DDD.

Ubicuous language:
- Race: a race is an event which a group of cars compete for winning.
- Circuit: a circuit is the place where race occurs.
- Car: a car is a race participant.
- Driver: a driver is a person who drives a car.
- Classification: it is a entity which has to control the times and positions of the cars.
- Position: defines the ranking of a car in the competition.
- CarIncident: describes a problem related to a car.
- TrackStatus: defines the status of de track. For instance: Dry, Wet, etc.
- Tyre: define the kind of the tyre of a car. For instance: Dry, Medium, Rain.

Entities:
- Race
- Circuit
- Car
- Driver
- Classification

Object Values: 
- Position
- CarIncident
- TrackStatus
- Tyre

Aggregates:
- Car -> (Driver)
- Race -> (Circuit, Classification)
- Classification -> (Position)

Domain Services:
- TyreStatusCalculator: calculate the degradation and grip of a tyre from the status of the track


The rules of the domain are validated by the unit tests.


