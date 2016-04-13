package com.ihasama.ohtu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App 
{
    public static void main( String[] args )
    {

        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");

        App application = ctx.getBean(App.class);
        application.run();
    }

    private void run()
    {
        System.out.println("Hello world!");
    }
}
