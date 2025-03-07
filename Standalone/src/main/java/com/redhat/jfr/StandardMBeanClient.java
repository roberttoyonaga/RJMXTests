/*
 * Copyright (c) 2025, Red Hat, Inc.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of the Red Hat GraalVM Testing Suite (the suite).
 *
 * The suite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * The suite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the suite.  If not, see <https://www.gnu.org/licenses/>.
 */
package main.java.com.redhat.jfr;

import javax.management.MBeanServerConnection;

import javax.management.JMX;
import javax.management.ObjectName;

/**
 * This is a JMX client. It is almost identical to main.java.com.redhat.jfr.JfrTesterInvoke, except it invoke operations directly on the
 * FlightRecorderMXBean instead of through the MBeanServerConnection reflectively.
 */
public class StandardMBeanClient {
    public static void main(String args[]) throws Exception {
        MBeanServerConnection mbsc = JmxUtils.getLocalMBeanServerConnectionStatic(args);
        System.out.println("made connection: " + mbsc.toString());
        ObjectName objectName = new ObjectName("com.jmx.test.basic:name=simple");
        StandardMBeanServer.SimpleMBean simpleMBean = JMX.newMBeanProxy(mbsc, objectName, StandardMBeanServer.SimpleMBean.class, false);
        System.out.println(simpleMBean.print());
    }
}
