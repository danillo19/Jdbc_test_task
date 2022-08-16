# Jdbc_test_task
Simple Java app with Postgres database(via JDBC).
# Table of contents
- [Introduction](https://github.com/danillo19/Jdbc_test_task/blob/master/README.md#introduction)
- [Requirements](https://github.com/danillo19/Jdbc_test_task/blob/master/README.md#requirements)
- [Quick start](https://github.com/danillo19/Jdbc_test_task/blob/master/README.md#quick-start)
- [How to work with it](https://github.com/danillo19/Jdbc_test_task/blob/master/README.md#how-to-work-with-it)

## Introduction
Simple Java + Postgresql (inside docker container) application, where you can search by names of customers and get some stats.

## Requirements
App can be run locally, requirements a listed below.

### Local
- Java8
- [Docker](https://www.docker.com/products/docker-desktop/)

## Quick start
### Configure database

Build docker image
```
$ docker build -t test_task .
```
Start container with environment arguments.
```
$   docker run -d --name ai_test -p 5433:5432 -e POSTGRES_PASSWORD=qwerty -e POSTGRES_USER=postgres -e POSTGRES_DB=task test_task:latest
```
By default app works with `POSTGRES_PASSWORD=qwerty`, `POSTGRES_USER=postgres` and `POSTGRES_DB=task`.
Postgres database started on port `5432` (you can specify external port :)).

### Run app
```
$ mvn clean package
$ java -jar target/TestTask-1.0-SNAPSHOT.jar search input.json output.json
```
or
```
$ java -jar target/TestTask-1.0-SNAPSHOT.jar stats input.json output.json
```
## How to work with it
Some testcases are in `src/main/resources/testcases` directory. You can copy it into input.json and run it to see how app works. Result will be in output.json.




