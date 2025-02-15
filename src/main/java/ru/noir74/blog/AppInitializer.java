package ru.noir74.blog;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.noir74.blog.configurations.WebApplicationConfig;

import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebApplicationConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = (ServletRegistration.Dynamic) servletContext.addServlet("dispatcher", dispatcherServlet);

        MultipartConfigElement multipartConfig = (new MultipartConfigElement(
                "D:\\YandexDisk\\mine\\IdeaProjects\\jm-sprint3\\src\\main\\resources\\images",
                10000000,
                20000000,
                5000000
        ));

        registration.setMultipartConfig(multipartConfig);

        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }

    public static void main(String[] args) throws LifecycleException, ServletException {
        Tomcat tomcat = new Tomcat();
        tomcat.setSilent(true);
        tomcat.getConnector().setPort(9090);
        tomcat.getConnector().setURIEncoding("UTF-8");

        Context tomcatContext = tomcat.addContext("", null);

        AppInitializer initializer = new AppInitializer();
        initializer.onStartup(tomcatContext.getServletContext());

        tomcat.start();
        tomcat.getServer().await();
    }
}