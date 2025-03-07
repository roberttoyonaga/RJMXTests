package main.java.com.redhat.jfr;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class SimpleJmx {
    public static void main(String args[]) throws Exception {
        ObjectName objectName = new ObjectName("com.jmx.test.basic:name=simple");
        Simple simple = new Simple();
        simple.setName("someName");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(simple, objectName);
        while (true) {
            Thread.sleep(1000);
            System.out.println("JMX server running...");
        }
    }

    public static interface SimpleMBean {
        String getName();

        void setName(String name);

        String print();
    }

    static class Simple implements SimpleMBean {
        private String name;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String print() {
            return "Print output " + name;
        }
    }
}
