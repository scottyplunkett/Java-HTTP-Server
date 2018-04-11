# Java-HTTP-Server
[![Build Status](https://travis-ci.org/scottyplunkett/Java-HTTP-Server.svg?branch=master)](https://travis-ci.org/scottyplunkett/Java-HTTP-Server)

Java HTTP Server from scratch using raw built-in classes.

# To compile and run from source:
## Clone (or fork then clone...) the repository
```
git clone https://github.com/scottyplunkett/Java-HTTP-Server.git
```
 ## Change directory into the Java-HTTP-Server Root
 ```
cd Java-HTTP-Server
``` 
 ## Compile source files and place into target directory
```
javac src/com/scottyplunkett/server/*.java -d classes
```
## Generate artifact (.jar)
```
jar cvfm HTTPServer.jar src/META-INF/MANIFEST.MF classes/com/scottyplunkett/server/*.class
```
## Run the jar
```
java -cp HTTPServer.jar com/scottyplunkett/server/HTTPServer 4000
```
