# RJMXTests
These tests explicitly check that remote JMX is working in Native Images. They also pay special attention to the FlightRecorderMXBean.

## Set up auth files
Restrict access to the authentication files. Remote JMX requires this, but it only needs to be done once. 
```
chmod 600 jmxremote/jmxremote.password
chmod 600 jmxremote/jmxremote.access
```

## Environment
```
export GRAALVM_HOME=<path>
export JAVA_HOME=<path>
```
## Compile

```
mvn package -Pjfrtestserver
mvn package -Pjfrtestclient
mvn package -Pjfrtestinvokeclient
mvn package -Pplatformbeantestclient
```

## Run a Native Server
Start this in another terminal.

This command starts the server with SSL and password authentication. 
```
target/jfrtestserver \
-Dcom.sun.management.jmxremote.authenticate=true \
-Dcom.sun.management.jmxremote.access.file=jmxremote/jmxremote.access \
-Dcom.sun.management.jmxremote.password.file=jmxremote/jmxremote.password \
-Djava.rmi.server.hostname=localhost \
-Dcom.sun.management.jmxremote.port=9998 \
-Dcom.sun.management.jmxremote.rmi.port=9998 \
-Dcom.sun.management.jmxremote.ssl=true \
-Dcom.sun.management.jmxremote.ssl.need.client.auth=true \
-Dcom.sun.management.jmxremote.local.only=false \
-Dcom.sun.management.jmxremote=true \
-Djavax.net.ssl.keyStore=ssl/serverkeystore \
-Djavax.net.ssl.keyStorePassword=serverpass \
-Djavax.net.ssl.trustStore=ssl/servertruststore \
-Djavax.net.ssl.trustStorePassword=servertrustpass \
-Dcom.sun.management.jmxremote.registry.ssl=true 
```

This command starts the server with only password authentication.
```
target/jfrtestserver \
-Dcom.sun.management.jmxremote.authenticate=true \
-Dcom.sun.management.jmxremote.access.file=jmxremote/jmxremote.access \
-Dcom.sun.management.jmxremote.password.file=jmxremote/jmxremote.password \
-Djava.rmi.server.hostname=localhost \
-Dcom.sun.management.jmxremote.port=9998 \
-Dcom.sun.management.jmxremote.ssl=false \
-Dcom.sun.management.jmxremote.local.only=false 
```

## Run a Native Client
Start this in another terminal.
```
target/<jfrtestclient/jfrtestinvokeclient/platformbeantestclient> 9998 ssl \
-Djavax.net.ssl.keyStore=ssl/clientkeystore \
-Djavax.net.ssl.keyStorePassword=clientpass \
-Djavax.net.ssl.trustStore=ssl/clienttruststore \
-Djavax.net.ssl.trustStorePassword=clienttrustpass

```

You can omit `ssl` in the above commands to run without ssl.

`JfrTest` is the default testing client that exercises the FlightRecorderMXBean (code in JfrTester.java).

`JfrTestInvoke` is a client that exercises the FlightRecorderMXBean similar to `JfrTest`, but it uses invocations via reflection (code in JfrTestInvoke.java).

`PlatformBeanTestClient` tests various attributes on multiple different platform MXBeans (code in PlatformBeanTestClient.java).


## Run a Java Client 
Start this in another terminal
```
$JAVA_HOME/bin/java  \
-Djavax.net.ssl.keyStore=ssl/clientkeystore \
-Djavax.net.ssl.keyStorePassword=clientpass \
-Djavax.net.ssl.trustStore=ssl/clienttruststore \
-Djavax.net.ssl.trustStorePassword=clienttrustpass \
-cp /home/rtoyonag/IdeaProjects/RJMXTests/target/classes main.java.com.redhat.jfr.<JfrTest/JfrTestInvoke/PlatformBeanTestClient> 9998 ssl
```
