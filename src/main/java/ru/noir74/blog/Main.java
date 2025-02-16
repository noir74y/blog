package ru.noir74.blog;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setSilent(true);
        tomcat.getConnector().setPort(9090);
        tomcat.addWebapp("", new File("src/main/webapp/").getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();
    }
}