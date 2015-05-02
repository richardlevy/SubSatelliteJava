A light-weight satellite receiver for Subsonic.  

SubSonic jukebox mode allows you to play music direct on the server hardware.  This is great if your server is hooked up to a sound system, but if it isn't then playing your SubSonic music through your sound system means installing a separate server.

SubSatellite is a lightweight SubSonic client with a cut-down SubSonic Jukebox REST API.  You can install SubSatellite on a node compatible device connected to a sound source and stream music from your existing SubSonic server. 

SubSatellite is controlled using a REST API that is based on the SubSonic Jukebox REST API.  It is recommended that you use an existing SubSonic client to control SubSatellite.

Clients with SubSatellite support are:

* AVSub (iOS) - http://www.avsubapp.co.uk

See http://www.avsubapp.co.uk/subSatellite.html for further details.

SubSatellite uses a slightly modified version of Aurora.js, which can be found here - https://github.com/richardlevy/aurora.js

## Installation

Will add soon...

### Configuration

* In your Subsonic server, create a user for SubSatellite to use (see SubSonic setup)
* Port defaults to 4141 (can be overriden by setting property *jetty.port*)
* Connection details to connect SubSatellite to SubSonic are provided by client (AVSub) configuration.

## SubSonic Setup

* Create a user in Subsonic for SubSatellite (put these details into config.js in SubSatellite)
* Add a new transcoder (Settings > Transcoding)
  * Name = *flac audio*
  * Convert from = *mp3 ogg oga aac m4a wav wma aif aiff ape mpc shn*
  * Convert to = *flac*
  * Step 1 = *ffmpeg -i %s -f flac -*
  * **Untick** *Enable this transcoding for all existing and new players*
* Setup SubSatellite Player (Settings > Players)
  * Clone an existing player
  * Name new player *subSatellite*
  * Disable all Active transcoding **except** *flac audio*
  * Save
  * Ensure all existing players have *flac audio* transcoding disabled

You are now setup.

## REST API 0.1

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

* Full direct support for FLAC
  * This is why everything is transcoded to FLAC in Subsonic (see SubSonic setup)
* Limited direct MP3 support (investigating this)
* Issue with pause operation (issue raised)
* Cannot seek within a file

## Building

SubSatellite requires:

* Java 1.8
* Maven

Included in the source (in the webapp subfolder) is a local Maven repository with most dependancies included.

To build, execute the following command from the root:

'''mvn clean package'''

The JAR file will be built to ./subsatellite-server/target.

**Note** - The build file creates an uber-jar file.  Due to the nature of the app, which is a webapp running inside an embedded server, some dependancies are duplicated in the final JAR file.  I'm sure this could be fixed.

MVN Deploy

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/basicplayer3.0.jar -DgroupId=javazoom -DartifactId=basicPlayer -Dversion=3.0 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/jflac-1.2.jar -DgroupId=javazoom -DartifactId=jflac -Dversion=1.2 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/jl1.0.jar -DgroupId=javazoom -DartifactId=jl -Dversion=1.0 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/jmactritonusspi1.74.jar -DgroupId=javazoom -DartifactId=jmactritonusspi1 -Dversion=1.74 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/jogg-0.0.7.jar -DgroupId=javazoom -DartifactId=jogg -Dversion=0.0.7 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/jorbis-0.0.15.jar -DgroupId=javazoom -DartifactId=jorbix -Dversion=0.0.15 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/jspeex0.9.7.jar -DgroupId=javazoom -DartifactId=jspeedx -Dversion=0.9.7 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/kj_dsp1.1.jar -DgroupId=javazoom -DartifactId=kj_dsp -Dversion=1.1 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/mp3spi1.9.4.jar -DgroupId=javazoom -DartifactId=mp3spi -Dversion=1.9.4 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/tritonus_share.jar -DgroupId=javazoom -DartifactId=tritonus_share -Dversion=1.0 -Dpackaging=jar

mvn deploy:deploy-file -Durl=file:///Users/richardlevy/Development/subsatjava/subsatellite-webapp/repo -Dfile=lib/vorbisspi1.0.2.jar -DgroupId=javazoom -DartifactId=vorbisspi -Dversion=1.0 -Dpackaging=jar


MVN Install

mvn install:install-file -Dfile=lib/basicplayer3.0.jar -DgroupId=javazoom -DartifactId=basicPlayer -Dversion=3.0 -Dpackaging=jar

mvn install:install-file -Dfile=lib/jflac-1.2.jar -DgroupId=javazoom -DartifactId=jflac -Dversion=1.2 -Dpackaging=jar

mvn install:install-file -Dfile=lib/jl1.0.jar -DgroupId=javazoom -DartifactId=jl -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=lib/jmactritonusspi1.74.jar -DgroupId=javazoom -DartifactId=jmactritonusspi1 -Dversion=1.74 -Dpackaging=jar

mvn install:install-file -Dfile=lib/jogg-0.0.7.jar -DgroupId=javazoom -DartifactId=jogg -Dversion=0.0.7 -Dpackaging=jar

mvn install:install-file -Dfile=lib/jorbis-0.0.15.jar -DgroupId=javazoom -DartifactId=jorbix -Dversion=0.0.15 -Dpackaging=jar

mvn install:install-file -Dfile=lib/jspeex0.9.7.jar -DgroupId=javazoom -DartifactId=jspeedx -Dversion=0.9.7 -Dpackaging=jar

mvn install:install-file -Dfile=lib/kj_dsp1.1.jar -DgroupId=javazoom -DartifactId=kj_dsp -Dversion=1.1 -Dpackaging=jar

mvn install:install-file -Dfile=lib/mp3spi1.9.4.jar -DgroupId=javazoom -DartifactId=mp3spi -Dversion=1.9.4 -Dpackaging=jar

mvn install:install-file -Dfile=lib/tritonus_share.jar -DgroupId=javazoom -DartifactId=tritonus_share -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=lib/vorbisspi1.0.2.jar -DgroupId=javazoom -DartifactId=vorbisspi -Dversion=1.0 -Dpackaging=jar

Notes

Dependencies needed for javazoom classes

Needed to add addjars for logging and the basicplayer

Must be a combo to produce the right size jar

Need to add log config


