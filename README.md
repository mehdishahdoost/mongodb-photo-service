# mongodb-photo-service
It is a simple service to upload 
images on ```MongoDB```. 
<br>
<br>
Tech stack:
* ```Java 17``` <br>
* ```Spring boot 2.5.5``` <br>
* ```Testcontainers 1.16.0``` <br>

##Test the code!

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

##Run the service!
To run the service in a dedicated environment, 
make sure that you have correctly set access 
parameters to ```MongoDB``` in file ```application.yml```. 
Be sure to set the parameter ```database```.
After above settings, using the following command to run the service:
<br>
```
mvn spring-boot:run
```
