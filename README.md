
## Running the server locally
The application can be started using the following command -

``````````
	mvnw
``````````

## Manual Testing the application

The postman collection is created for manual testing. It is located in [documents/](documents/AssignmentJavaApp.postman_collection.json).
After authentication copy the token to collection specific environment variable and save.


## Testing

To launch your application's tests, run:

	````
	mvnw clean test
	````

### Generate Test Report

The tests report for this project can be generated using the following maven commands.

	````
	mvnw clean verify
	````
They're located in [target/site/jacoco/](target/site/jacoco/). To see the report open [target/site/jacoco/index.html](target/site/jacoco/index.html)] in a browser.


### Generate SonarQube Report

The sonar report for this project can be generated using the following maven commands.

	````
	mvnw sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login={sonartoken}
	````

