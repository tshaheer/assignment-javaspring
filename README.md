
## Running the server locally

First clone the source code form this repository to your workspace folder. 

`````
	$ git clone https://github.com/tshaheer/assignment-javaspring
`````

Navigate to the root folder of the application 'cd assignment-javaspring'

Then the application can be started using the following command

``````````
	mvnw
``````````

Before running above command clone the source code to 


## Manual Testing the application

The postman collection is created for manual testing. It is located in [documents/documents/AssignmentJavaApp.postman_collection.json](documents/AssignmentJavaApp.postman_collection.json).
After authentication copy the token to collection specific environment variable and save.


## Testing

To launch the application's tests, run:

````````
	mvnw clean test
````````

### Generate Test Report

The tests report for this project can be generated using the following maven commands.

``````
	mvnw clean verify
``````
They're located in [target/site/jacoco/](target/site/jacoco) folder. To see the report open [target/site/jacoco/index.html](target/site/jacoco/index.html) in a browser.


### Generate SonarQube Report

The sonar report for this project can be generated using the following maven commands.

```````
	mvnw sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login={sonartoken}
```````

Start the sonarqube server before running the above maven command.

As an alternative you can use Docker to run the sonar server. The project has a docker-compose file for the quick sonarqube server setup which is located in [docker/sonar.yml](docker/sonar.yml)

