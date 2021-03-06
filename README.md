# File Generator

The project is developed with java 11 using Springboot Framework mainly. Other technologies were used such as:

  - Swagger for API documentation.
  - JUnit for testing.
  - Docker for containers.

## Installation 

After cloning the repository, you will need to do:

You will need to install docker-compose with this command 
Mac: 
```
brew install docker-compose
```

Linux: 
```
apt install docker-compose
```

After installing docker compose you can run the project with:

```
docker-compose up
```

command inside a terminal in the main folder of the project. This application is made with Java and Spring Boot, so we have an embedded Tomcat server to be able to deploy it locally and also it will be running inside a docker container.


## Endpoints Information

The project has swagger, so when you run the application, you can check the information of the endpoints in this url http://localhost:8081/swagger-ui/index.html?url=/v3/api-docs&validatorUrl=#/. Here you can see the request and response json structure and how to build it. Also you have a button "try it out" so you can test the endpoint. If you want to try it in a more traditional way, swagger creates the curl comand for you!
Doing a get method from the browser http://localhost:8081/posts/1?extension=json will download a json file an doing http://localhost:8081/posts/1?extension=xml will download an xml file.

## Front End

To try it out with a Front end you could use this repository: https://github.com/oevm1992/file-generator-front. It has it's own steps to run it.

## Notes

The backend was quite fun to develop. I hadn't worked with XML for a long time, so I tried quite a few different libraries until I got the one that best suited the needs of the project. Unfortunately I had many problems using the lombok libraries due to problems with the XML libraries, they did not work correctly. One thing to improve is to be able to use lombok with these libraries since it would give us a more readable and clean code.


