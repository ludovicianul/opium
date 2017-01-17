# opium
Opium builds on top of Appium and allows remote test execution and device interaction across local networks. 
Opium has 2 components:
- this project, also called the Opium Hive: allows remote device registration using [Opium Agents](https://github.com/ludovicianul/opium.agent) and exposes a simple REST api to see the available devices
- [Opium Agents](https://github.com/ludovicianul/opium.agent) which register mobile devices and sends the relevant connection information to the Hive

# build the project

`mvn clean package`

# runnign the project
`java -jar opium.jar`
The project starts by default on port 2020

# available api
APIs exposed are available under `http://localhost:2020/swagger-ui.html`
The most important APIs
- `GET /device` which lists all the available devices registered at a certain moment
- `POST /execute` used to send the command to start Appium
- `DELETE /execute` used to stop Appium
Once Appium is started you can interact directly with the Appium server using the IP received in the `/device` response and using the port sent when Appium started.
A typical use case will look as follows:
- call `GET /device`
- pick an appropiate device based on Android version, device type, etc
- do a `POST /execute` and start the Appium server for that device by sending the Appium start paramters(like `-p 9999` for example), device hash and host IP address
- once Appium started, interact execute the tests against the Appium instance
- when tests are finished, stop the Appium instance using `DELETE /execute` by sending the running port(`9999` above for example), device hash and IP address of the host

# persistence
The Hive uses in memory persistence. Even if the Hive fails and needs restart, all agents will re-register within 10 seconds.

# health
The Hive continuosly monitors agents availability and will remove any unavailable device within 5 seconds of unavailability.

# limitations
Opium only works with Android devices. 

# pre-requisits
Opium needs [Opium Agents](https://github.com/ludovicianul/opium.agent) deployed on the computers were the Android devices will be connected. Furthermore, Appium needs to be installed in order to further run the automated tests.

# build
[![Build Status](https://snap-ci.com/ludovicianul/opium/branch/master/build_image)](https://snap-ci.com/ludovicianul/opium/branch/master)
