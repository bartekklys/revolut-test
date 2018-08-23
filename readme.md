To start the app simply run this command while in project directory:
```sh
java -jar target/revolut-1.0-SNAPSHOT.jar server revolut.yml
```

### Libraries used
| Library | Website |
| ------ | ------ |
| Dropwizard | https://www.dropwizard.io/1.3.5/docs/ |
| H2 Database Engine | http://www.h2database.com/html/main.html |
| Hibernate | http://hibernate.org/ |
| sl4j | https://www.slf4j.org/ |
| Project Lombok | https://projectlombok.org/ |
| TestNG | https://testng.org/doc/index.html |
| Mockito | https://site.mockito.org/ |
| AssertJ | http://joel-costigliola.github.io/assertj/ |

# Java/Scala Test
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
