## Task

### Hotel administrator.
Room list: roomType class (enum), number of beds(capacity).
List of guests: passport data, arrival and departure dates, room.
- Check-in: selection of a suitable room (subject to availability), registration, issuing a receipt.
- Departure: selecting all guests who are leaving today,
  freeing up space or processing delay in issuing an additional receipt.
  Possibility of early departure with recalculation.
- Search for a guest by an arbitrary characteristic.

[![Java CI with Maven](https://github.com/Lokankara/Hotel/actions/workflows/maven.yml/badge.svg)](https://github.com/Lokankara/Hotel/actions/workflows/maven.yml)

### Commands

#### Run jar with DB Postgresql 
`java "-Dspring.profiles.active=prod" -jar target/hotel-0.0.1-SNAPSHOT.jar --thin.dryrun`

#### Run test report 
`mvn test jacoco:report`

#### Run quality test
`mvn sonar:sonar`

#### Build jar
`mvn clean install -e`

### endpoints

#### Home
https://hotel-california.onrender.com/

#### H2
https://hotel-california.onrender.com/h2-console/