# Docker Internal Communication Sample

I visualized the internal communication among two docker containers.One is server and another is acts as a client.So here server exposed one socket server and listening and client is connecting with that socket server and broadcasting message on 1 second interval.The response and request are printing on log of the containers.

Some basic things about container to container communication.

- We can use container name with port as domain to access the container from another container
- We can use server name alias for that as well to connect
- We have to give a name to container otherwise name will be assigned randomly
- We have to expose the container name of server to container of client by env.So that client container can know the server container url to connect
- We have to expose the port
- We can define the build arguments like version etc during docker build


Docker build can also be done directly using docker compose file.In docker compose file we can define build section which has two fields.
- context : which is a folder path of Dockerfile
- dockerfile: Which is the name of docker file

The most important part is the network.We have to create one bridge network and have to put the containers into the same network by network argument.If we use docker compose file then we dont need to create the network manually.Docker compose file will create the network automatically.
But if we use docker run then we have to create the network manually.

for network in docker compose file we have to first declare a network on root object and then have to put a referrence of that on every service
## Build
As this is a spring boot project we have to use JAVA and jdk for this.But i already declared the needed things on dockerfile.So here i'm giving the commands for docker build

```
Build server app

docker build --build-arg JAR_NAME=demo-0.0.1-SNAPSHOT --build-arg version=1.0.0 -t docker-server:1.0.0 .
```

```
Build client app

docker build --build-arg JAR_NAME=demo-0.0.1-SNAPSHOT --build-arg version=1.0.0 -t docker-client:1.0.0 .
```
## Docker Run

We can run the images using docker run

```
Run docker server app

docker run -p 8080:6520 --env PORT=6520 --name socket-server.com --network test-socket  docker-server:1.0.0
```

```
Run docker client app

docker run -p 5050:6520 --env PORT=6520 --env SERVER_CONTAINER_NAME_WITH_PORT=socket-server.com:6520 --network test-socket docker-client:1.0.0
```
## Docker Compose

We can run the images docker compose file.

```
Run using docker compose

docker-compose -f docker-socket-compose.yml up
```

We can build and run the images using one single docker compose file.It will check for the source code into the defined directory and if not present it will try to download the project codebase from remote and then will build

```
Build and run images using docker compose

docker-compose -f docker-socket-compose-with-build.yml up --build
```
## Network Creation

To create a network on docker manually

```
Create a bridge network (bridge is default)

docker network create test-network
```
## Multi-Stage

I have used multi stage building process of docker using dockerfile.

Multi stage means compiling and building the project and generate the artifact in one stage.And on next stage just build the image using only that artifact.

The advantages of multi stage are-
- It reduces the image size
- It makes the script clean and robust
