#SubSatellite 

A small satellite receiver for Subsonic.  

SubSonic jukebox mode allows you to play music direct on the server hardware.  This is great if your server is hooked up to a sound system, but if it isn't then playing your SubSonic music through your sound system means installing a separate server.

SubSatellite is a small SubSonic client with a cut-down SubSonic Jukebox REST API.  You can install SubSatellite on a Java 1.8 compatible device connected to a sound source and stream music from your existing SubSonic server. 

SubSatellite is controlled using a REST API that is based on the SubSonic Jukebox REST API.  It is recommended that you use an existing SubSonic client to control SubSatellite.

Clients with SubSatellite support are:

* AVSub (iOS) - http://www.avsubapp.co.uk

See http://www.avsubapp.co.uk/subSatellite.html for further details.

SubSatellite uses JLGui (http://www.javazoom.net/jlgui/jlgui.html) classes to stream audio.

##Download

SubSatellite can be downloaded from http://www.avsubapp.co.uk/subSatellite.html 

## Installation

SubSatellite is packaged as a single JAR file and can be run with the following command

```java -jar subsatellite-0.1.jar```

By default, SubSatellite runs on port 4141

## Configuration

* In your Subsonic server, create a user for SubSatellite to use
* Port defaults to 4141 (can be overriden by setting property *jetty.port*)
* Connection details to connect SubSatellite to SubSonic are provided by client (AVSub) configuration.

## Client Setup

The client passes information to SubSatellite about which Subsonic it should use.  This allows for a very simple setup for SubSatellite.

See http://www.avsubapp.co.uk/subSatellite.html for AVSub specific details.

## REST API 0.2

```
/rest/satelliteControl.view/status
/rest/satelliteControl.view/set/{ids}
/rest/satelliteControl.view/start
/rest/satelliteControl.view/stop
/rest/satelliteControl.view/get
/rest/satelliteControl.view/skip/{index}/{offset}
/rest/satelliteControl.view/setGain/{gain}
/rest/credentials/{host}/{username}/{password}"
```

## Current Limitations

This project is a minimum viable prototype.  It has the following limitations:

* Cannot seek within a file
* Error reporting is minimal

## Building

SubSatellite requires:

* Java 1.8
* Maven

Included in the source (in the webapp subfolder) is a local Maven repository with most dependancies included.

To build, execute the following command from the root:

'''mvn clean package'''

The JAR file will be built to ./subsatellite-server/target.

**Note** - The build file creates an uber-jar file.  Due to the nature of the app, which is a webapp running inside an embedded server, some dependancies are duplicated in the final JAR file.  I'm sure this could be fixed.



