## Udacity Java Programmer - Project 3 - UdaSecurity

UdaSecurity is a Java application that simulates a security system for detecting cats. It uses image analysis to determine if a given image contains a cat and triggers an alarm if a cat is detected.

### Features

- Arming and disarming the security system
- Activating and deactivating sensors
- Analyzing images for cat detection
- Displaying the current status of the security system

### Project Screenshots

<img src="./readme-media/snap.png" />

![](./executable_jar.png)

### Project Requirements

- Splitting project into modules
- Writing Unit tests
- Improving Code Coverage & Debugging Functionality Issues
- Doing Static Analysis & Fixing Code Vulnerabilities
- Bulding Single Executible Jar - Packaging All Modules

### Dependencies

```shell
$ java -version

java version "20.0.2" 2023-07-18
Java(TM) SE Runtime Environment (build 20.0.2+9-78)
Java HotSpot(TM) 64-Bit Server VM (build 20.0.2+9-78, mixed mode, sharing)
```

```
$ mvn -version

Apache Maven 3.9.4 (dfbb324ad4a7c8fb0bf182e6d91b0ae20e3d2dd9)
```

### Project Commands

All spotbug report html files will be generated in respective `<module>/target/site` directories.

```shell
# Clear Previous Builds
mvn clean

# Clear Maven Caches - Troubleshooting
mvn clean install

# Build Project
mvn compile

# Run All Unit Tests
mvn test

# Generate Reports & Spotbug Analysis
mvn site

# Build Single Executable Jar File
mvn package

# Run Jar File
java -jar security/target/security-1.0-SNAPSHOT-jar-with-dependencies.jar
```















