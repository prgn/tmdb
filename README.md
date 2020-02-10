# tmdb
This automation framework is primarily TestNg and RestAssured based. TestNg helps make this env and suite independent, while RestAssured provides a stable java based library to work with RestApis.

## Run the test 
mvn clean test  -P integrationtest -DenvType=qa -Dsuite=MovieAPI

When the above command is executed :

· integrationtest profile is selected to run the suite - MovieAPI.xml

· enviroment file qa.config.properties is chosen to dyamically insert the environment related information like the url and other env sensitive data

## Domain
These autoamtion tests focus on the  is a free community built database of movie and TV meta data. : https://developers.themoviedb.org/3/getting-started/introduction

## Software limitation
Due to more groovy realted integration issues found JRE version has been lowered to JRE8.
