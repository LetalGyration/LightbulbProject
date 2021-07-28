# LightbulbProject
As already stated this project is an implementation of list with rooms and made of 3 separate Docker containers that holds:

- PostgreSQL database
- Java backend (Spring Boot)
- Angular frontend

The entry point for a regular user is a website which is available with: **http://localhost:8081/**
### Prerequisites

In order to run this application you need to install two tools: **Docker** & **Docker Compose**.

Instructions how to install **Docker** on [Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/), [Windows](https://docs.docker.com/docker-for-windows/install/) , [Mac](https://docs.docker.com/docker-for-mac/install/) .

**Dosker Compose** is already included in installation packs for *Windows* and *Mac*, so only Linux users needs to follow [this instructions](https://docs.docker.com/compose/install/) .




### How to run it?

An entire application can be started with a single command in a terminal:

```
$ docker-compose up
```

If you want to stop it use the following command:

```
$ docker-compose down
```

PostgreSQL database contains only single schema with table - room.

After running the app it can be accessible using this connectors:


- Host: *localhost*
- Database: *room*
- User: *postgres*
- Password: *12345*


Like other parts of the application Postgres database is containerized and
the definition of its Docker container can be found in
*docker-compose.yml* file.

```yml
  db-postgres:
    image: "postgres:13.3"
    container_name: db-postgres
    ports:
      - "5432:5432"


    environment:
      - POSTGRES_DB=room
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=12345
    networks:
      - backend
```



#### Backend

This is a Spring Boot (Java) based application that connects with a
database and expose the REST endpoints that can be used by
frontend. It supports multiple HTTP REST methods like GET, POST, PUT for resource - room.

This app is also dockerized and its definition can be found
in a file *Dockerfile*. 



#### Frontend

This is a real entrypoint for a user where they can manipulate rooms. It consumes the REST API endpoints provided by
the backend.

It can be entered using link: **http://localhost:8081/**

#### Enpoints

GET - */api/rooms*
GET - */api/rooms/id*
POST - */api/rooms*
PUT - */api/rooms/id/*

#### Improvements

Opportunities to improve the application:
1. using Amazon s3 to work with images
2. Improving the auto-update of the bulb component using sockets.
