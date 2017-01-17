# opium
Opium builds on top of Appium and allows remote test execution and device interaction across local networks. 
Opium has 2 components:
- this project, also called the Opium Hive: allows remote device registration using Opium Agents and exposes a simple REST api to see the available devices
- Opium Agents which register mobile devices and sends the relevant connection information to the Hive

# build the project

`mvn clean package`

# runnign the project
`java -jar opium.jar`
The project starts by default on port 2020

# available api
APIs exposed are available under `http://localhost:2020/swagger-ui.html`
The most important API is `/devices` which lists all the available devices registered at a certain moment. Using details received in the response you can interact directly with the specific host using the Opium Agent's API.

# persistence
The Hive uses in memory persistence. Even if the Hive fails and needs restart, all agents will re-register within 10 seconds.

# health
The Hive continuosly monitors agents availability and will remove any unavailable device within 5 seconds of unavailability.

# limitations
Opium only works with Android devices. 

# pre-requisits
Opium needs Opium Agents deployed on the computers were the Android devices will be connected. Furthermore, Appium needs to be installed in order to further run the automated tests.

# build
[![Build Status](https://snap-ci.com/ludovicianul/opium/branch/master/build_image)](https://snap-ci.com/ludovicianul/opium/branch/master)
