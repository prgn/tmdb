# tmdb
## Run the test 
mvn clean test  -P integrationtest -DenvType=qa -Dsuite=MovieAPI

When the above command is executed :

· integrationtest profile is selected to run the suite - MovieAPI.xml

· enviroment file qa.config.properties is chosen to dyamically insert the environment related information like the url and other env sensitive data


