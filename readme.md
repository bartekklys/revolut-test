# Revolut Java/Scala Test

[![N|Solid](https://dl.myket.ir/newresizing/resize/medium/png/icon/com.revolut.revolut.png)](https://nodesource.com/products/nsolid)

Design and implement a RESTful API (including data model and the backing implementation) for money
transfers between accounts.

**Explicit requirements:**
- keep it simple and to the point (e.g. no need to implement any authentication, assume the APi is
invoked by another internal system/service)
- use whatever frameworks/libraries you like (except Spring, sorry!) but don't forget about the
requirement #1
- the datastore should run in-memory for the sake of this test
- the final result should be executable as a standalone program (should not require a pre-installed
container/server)
- demonstrate with tests that the API works as expected

**Implicit requirements:**
- the code produced by you is expected to be of high quality.
- there are no detailed requirements, use common sense.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing purposes.

### Prerequisites

What things you need to install the software and how to install them

```
java -version
```

### Installing

A step by step example that tell you how to get a development env running

To build executable jar

```
mvn clean install
```

To run the app

```
java -jar target/revolut-1.0-SNAPSHOT.jar server revolut.yml
```

## Running the tests

To run unit tests
```
mvn test
```

## Built With

| Library | Website | Description |
| ------ | ------ | ------ |
| Maven | https://maven.apache.org/ | Build automation tool. |
| Dropwizard | https://www.dropwizard.io/1.3.5/docs/ | Java framework for developing ops-friendly, high-performance, RESTful web services. |
| H2 Database Engine | http://www.h2database.com/html/main.html | Relational database management system written in Java. It can be embedded in Java applications or run in the client-server mode. |
| Hibernate | http://hibernate.org/ | An object-relational mapping tool for the Java.  |
| sl4j | https://www.slf4j.org/ | Simple Logging Facade for Java. |
| Project Lombok | https://projectlombok.org/ |  Java library that automatically plugs into editor and build tools, spicing up Java code. |
| TestNG | https://testng.org/doc/index.html |  Testing framework inspired from JUnit and NUnit but introducing some new functionalities. |
| Mockito | https://site.mockito.org/ | Allows the creation of test double objects in automated unit tests for the purpose of TDD or BDD. |
| AssertJ | http://joel-costigliola.github.io/assertj/ | Fluent assertions for Java. |

## Authors

* **Bartosz Kłys** - *klys.bartosz@gmail.com*
