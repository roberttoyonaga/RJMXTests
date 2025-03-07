package org.acme.getting.started;

import jdk.jfr.*;

@Name("com.redhat.CustomEvent")
@Label("Custom Event")
@Description("An event with a string payload")
@StackTrace(false)
public class CustomEvent extends Event {
    @Label("Message")
    public String message;
}
