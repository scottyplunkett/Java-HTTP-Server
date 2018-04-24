# Java-HTTP-Server
[![Build Status](https://travis-ci.org/scottyplunkett/Java-HTTP-Server.svg?branch=master)](https://travis-ci.org/scottyplunkett/Java-HTTP-Server)

HTTP Server built with JDK9 using only Java SE classes.

# To run the server:
## 1. Clone the repo
```
git clone https://github.com/scottyplunkett/Java-HTTP-Server.git
```
## 2. Change Directory into the project root
```
cd Java-HTTP-Server
```
## 3. Compile `.java` files into `.class` files
```
javac -sourcepath ./src ./src/com/scottyplunkett/server/HTTPServer.java
```
## 4. Start the server
```
java -cp src/ com.scottyplunkett.server.HTTPServer
```
## 5. Open your browser and navigate to `localhost:5000`

### _You may optionally specify a port by appending the `-p` flag and a port number as arguments._ 
_Ex._ 
```
java -cp src/ com.scottyplunkett.server.HTTPServer -p 4000
```
