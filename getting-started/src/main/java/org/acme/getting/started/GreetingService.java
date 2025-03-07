package org.acme.getting.started;

import com.sun.management.GarbageCollectorMXBean;
import jakarta.enterprise.context.ApplicationScoped;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class GreetingService {
    private static MBeanServerConnection getLocalMBeanServerConnectionStatic() {
        try {
            JMXServiceURL jmxUrl =  new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + "localhost" + ":" + 9998 +  "/jmxrmi");
            Map<String, Object> env = new HashMap<>();

            JMXConnector connector = JMXConnectorFactory.connect(jmxUrl, env);
            return connector.getMBeanServerConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String greeting(String name)  throws MalformedObjectNameException {
        ObjectName objectName = new ObjectName("java.lang:type=Threading");
        MBeanServerConnection mbsc = getLocalMBeanServerConnectionStatic();
//        ThreadMXBean o = JMX.newMBeanProxy(null,null, ThreadMXBean.class,false);
        ThreadMXBean o = JMX.newMBeanProxy(mbsc,objectName, ThreadMXBean.class,false);
        return "hello " + name + o.toString();
    }

}
