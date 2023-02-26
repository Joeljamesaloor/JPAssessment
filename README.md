# JP Morgan Assessment - Cucumber Java Selenium Framework

This Project is a Cucumber Java Selenium framework which can be used for web automation testing.

Objective
The objective of this project is to test the authenticity of an article published in an online news application by comparing it with search results obtained from a public search engine. If 70% of the search results match with the original article, the article is assumed to be valid, otherwise it is likely to be fake.

Requirements
In order to execute this project, the following requirements must be met:

Java 8 or higher
Maven 3.6.0 or higher
Eclipse or any other Java IDE
Chrome and Firefox browsers installed
Chrome and Firefox drivers installed and added to the system/project path (src/main/resources/drivers)
Git (optional)

Setup
Clone the repository or download the zip file and extract it to your local system.
Open your IDE and import the project as a Maven project.
Install the required dependencies by running the following command in the terminal:

< mvn clean install >

Update the src/test/resources/config.properties file with the required browser and application URL details.

Execution
To run the test suite, execute the following command in the terminal:

< mvn test >

The test results will be generated in the target/cucumber-html-report folder.

Project Structure
src/test/java - Contains the Java classes for step definitions and test runners.
src/test/resources - Contains the feature files, properties files and other resources required for the framework.
target - Contains the test reports and other generated files.


Credits
This framework was developed by Joel James.
