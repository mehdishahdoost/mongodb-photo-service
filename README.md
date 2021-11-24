# mongodb-photo-service

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)
[![SpringBoot](https://img.shields.io/badge/SpringBoot-2.5.5-green.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)
[![Testcontainers](https://img.shields.io/badge/Testcontainers-1.16.0-blue.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)


It is a simple service to upload
images on ```MongoDB```.
<br>
<br>
Tech stack:
* ```Java 17``` <br>
* ```Spring boot 2.5.5``` <br>
* ```Testcontainers 1.16.0``` <br>

## Test the code!

To test the service use: <br>
```
mvn -Dtest=PhotoServiceTest test
```

To test the controller use: <br>
```
mvn -Dtest=PhotoControllerTest test
```
As ```testcontainers``` is used to test the
repository, be sure ```docker``` is running on
your machine. Check the image name for ```MongoDB```.
Change it in the code in case of necessity.
<br>
```
mvn -Dtest=PhotoRepositoryTest test
```

## Run the service!
To run the service in a dedicated environment,
make sure that you have correctly set access
parameters to ```MongoDB``` in file ```application.yml```.
Be sure to set the parameter ```database```.
After above settings, using the following command to run the service:
<br>
```
mvn spring-boot:run
```
## API
List of uploaded photo:
```
GET: /v1/photo/
```
Get a photo by specific id:
```
GET: /v1/photo/{id}
```
Upload a new photo using two parameters ```title``` and ```image```:
```
POST: /v1/photo/add
```
